package cm.aptoide.pt.editorialList;

import android.support.annotation.VisibleForTesting;
import cm.aptoide.accountmanager.Account;
import cm.aptoide.accountmanager.AptoideAccountManager;
import cm.aptoide.pt.crashreports.CrashReport;
import cm.aptoide.pt.home.EditorialHomeEvent;
import cm.aptoide.pt.presenter.Presenter;
import cm.aptoide.pt.presenter.View;
import cm.aptoide.pt.reactions.network.ReactionsResponse;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import rx.Single;

public class EditorialListPresenter implements Presenter {

  private final EditorialListView view;
  private final EditorialListManager editorialListManager;
  private final AptoideAccountManager accountManager;
  private final EditorialListNavigator editorialListNavigator;
  private final EditorialListAnalytics editorialListAnalytics;
  private final CrashReport crashReporter;
  private final Scheduler viewScheduler;

  public EditorialListPresenter(EditorialListView editorialListView,
      EditorialListManager editorialListManager, AptoideAccountManager accountManager,
      EditorialListNavigator editorialListNavigator, EditorialListAnalytics editorialListAnalytics,
      CrashReport crashReporter, Scheduler viewScheduler) {
    this.view = editorialListView;
    this.editorialListManager = editorialListManager;
    this.accountManager = accountManager;
    this.editorialListNavigator = editorialListNavigator;
    this.editorialListAnalytics = editorialListAnalytics;
    this.crashReporter = crashReporter;
    this.viewScheduler = viewScheduler;
  }

  @Override public void present() {
    onCreateLoadViewModel();
    handleImpressions();
    handleEditorialCardClick();
    handlePullToRefresh();
    handleRetryClick();
    handleBottomReached();
    handleUserImageClick();
    handleReactionClick();
    handleLongPressReactionButton();
    handleUserReaction();
    handleLogInClick();
    loadUserImage();
  }

  private void handleLongPressReactionButton() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.reactionButtonLongPress())
        .doOnNext(homeEvent -> {
          editorialListAnalytics.sendReactionButtonClickEvent();
          view.showReactionsPopup(homeEvent.getCardId(), homeEvent.getGroupId(),
              homeEvent.getBundlePosition());
        })
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(lifecycleEvent -> {
        }, crashReporter::log);
  }

  private Single<List<CurationCard>> loadReactionModel(String cardId, String groupId) {
    return editorialListManager.loadReactionModel(cardId, groupId)
        .observeOn(viewScheduler)
        .doOnSuccess(curationCards -> view.update(curationCards));
  }

  @VisibleForTesting public void onCreateLoadViewModel() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .doOnNext(created -> view.showLoading())
        .flatMap(__ -> loadEditorialAndReactions(false, false))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(__ -> {
        }, crashReporter::log);
  }

  private Observable<List<CurationCard>> loadEditorialAndReactions(boolean loadMore,
      boolean refresh) {
    return loadEditorialListViewModel(loadMore, refresh).toObservable()
        .flatMapIterable(EditorialListViewModel::getCurationCards)
        .flatMapSingle(
            curationCard -> loadReactionModel(curationCard.getId(), curationCard.getType()));
  }

  @VisibleForTesting public void loadUserImage() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> accountManager.accountStatus())
        .flatMap(account -> getUserAvatar(account))
        .observeOn(viewScheduler)
        .doOnNext(userAvatarUrl -> {
          if (userAvatarUrl != null) {
            view.setUserImage(userAvatarUrl);
          } else {
            view.setDefaultUserImage();
          }
          view.showAvatar();
        })
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(__ -> {
        }, throwable -> crashReporter.log(throwable));
  }

  @VisibleForTesting public void handleEditorialCardClick() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(__ -> view.editorialCardClicked()
            .observeOn(viewScheduler)
            .doOnNext(click -> {
              editorialListAnalytics.sendEditorialInteractEvent(click.getCardId(),
                  click.getBundlePosition());
              editorialListNavigator.navigateToEditorial(click.getCardId());
            })
            .retry())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(homeClick -> {
        }, throwable -> crashReporter.log(throwable));
  }

  @VisibleForTesting public void handlePullToRefresh() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.refreshes()
            .flatMap(__ -> loadEditorialAndReactions(false, true))
            .retry())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(bundles -> {
        }, crashReporter::log);
  }

  @VisibleForTesting public void handleRetryClick() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(viewCreated -> view.retryClicked()
            .observeOn(viewScheduler)
            .doOnNext(bottom -> view.showLoading())
            .flatMap(__ -> loadEditorialAndReactions(false, true)))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(notificationUrl -> {
        }, crashReporter::log);
  }

  @VisibleForTesting public void handleImpressions() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.visibleCards()
            .observeOn(viewScheduler)
            .doOnNext(editorialListEvent -> editorialListAnalytics.sendEditorialImpressionEvent(
                editorialListEvent.getCardId(), editorialListEvent.getPosition())))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(__ -> {
        }, crashReporter::log);
  }

  @VisibleForTesting public void handleBottomReached() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.reachesBottom()
            .filter(__ -> editorialListManager.hasMore())
            .observeOn(viewScheduler)
            .doOnNext(bottomReached -> view.showLoadMore())
            .flatMap(bottomReach -> loadEditorialAndReactions(true, false))
            .retry())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(bundles -> {
        }, throwable -> crashReporter.log(throwable));
  }

  private Single<EditorialListViewModel> loadEditorialListViewModel(boolean loadMore,
      boolean refresh) {
    return editorialListManager.loadEditorialListViewModel(loadMore, refresh)
        .observeOn(viewScheduler)
        .doOnSuccess(editorialListViewModel -> {
          if (!editorialListViewModel.isLoading()) {
            view.hideLoading();
          }
          if (editorialListViewModel.hasError()) {
            if (editorialListViewModel.getError() == EditorialListViewModel.Error.NETWORK) {
              view.showNetworkError();
            } else {
              view.showGenericError();
            }
          } else {
            if (refresh) {
              view.hideRefresh();
              view.update(editorialListViewModel.getCurationCards());
            } else {
              view.populateView(editorialListViewModel.getCurationCards());
            }
          }
          view.hideLoadMore();
        })
        .map(editorialViewModel -> editorialViewModel);
  }

  @VisibleForTesting public void handleReactionClick() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.reactionsButtonClicked())
        .observeOn(viewScheduler)
        .flatMap(editorialHomeEvent -> editorialListManager.isFirstReaction(
            editorialHomeEvent.getCardId(), editorialHomeEvent.getGroupId())
            .flatMapSingle(isFirstReaction -> singlePressReactionButtonAction(editorialHomeEvent,
                isFirstReaction)))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(lifecycleEvent -> {
        }, crashReporter::log);
  }

  @VisibleForTesting public void handleUserReaction() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.reactionClicked())
        .flatMapSingle(
            reactionsHomeEvent -> editorialListManager.setReaction(reactionsHomeEvent.getCardId(),
                reactionsHomeEvent.getGroupId(), reactionsHomeEvent.getReaction())
                .observeOn(viewScheduler)
                .doOnSuccess(reactionsResponse -> handleReactionsResponse(reactionsResponse))
                .flatMap(__ -> loadReactionModel(reactionsHomeEvent.getCardId(),
                    reactionsHomeEvent.getGroupId())))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(lifecycleEvent -> {
        }, crashReporter::log);
  }

  private void handleReactionsResponse(ReactionsResponse reactionsResponse) {
    if (reactionsResponse.wasSuccess()) {
      editorialListAnalytics.sendReactedEvent();
    } else if (reactionsResponse.reactionsExceeded()) {
      view.showLogInDialog();
    } else {
      view.showErrorToast();
    }
  }

  @VisibleForTesting public void handleLogInClick() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.snackLogInClick())
        .doOnNext(homeEvent -> editorialListNavigator.navigateToLogIn())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(lifecycleEvent -> {
        }, crashReporter::log);
  }

  @VisibleForTesting public void handleUserImageClick() {
    view.getLifecycleEvent()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.imageClick()
            .observeOn(viewScheduler)
            .doOnNext(account -> editorialListNavigator.navigateToMyAccount())
            .retry())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(__ -> {
        }, throwable -> crashReporter.log(throwable));
  }

  private Observable<String> getUserAvatar(Account account) {
    String userAvatarUrl = null;
    if (account != null && account.isLoggedIn()) {
      userAvatarUrl = account.getAvatar();
    }
    return Observable.just(userAvatarUrl);
  }

  private Single<List<CurationCard>> singlePressReactionButtonAction(
      EditorialHomeEvent editorialHomeEvent, boolean isFirstReaction) {
    if (isFirstReaction) {
      editorialListAnalytics.sendReactionButtonClickEvent();
      view.showReactionsPopup(editorialHomeEvent.getCardId(), editorialHomeEvent.getGroupId(),
          editorialHomeEvent.getBundlePosition());
      return Single.just(Collections.emptyList());
    } else {
      editorialListAnalytics.sendDeleteEvent();
      return editorialListManager.deleteReaction(editorialHomeEvent.getCardId(),
          editorialHomeEvent.getGroupId())
          .flatMap(__ -> loadReactionModel(editorialHomeEvent.getCardId(),
              editorialHomeEvent.getGroupId()));
    }
  }
}
