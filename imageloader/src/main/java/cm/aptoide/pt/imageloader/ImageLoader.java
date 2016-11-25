/*
 * Copyright (c) 2016.
 * Modified by SithEngineer on 22/08/2016.
 */

package cm.aptoide.pt.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.widget.ImageView;
import cm.aptoide.pt.preferences.Application;
import cm.aptoide.pt.utils.AptoideUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.target.NotificationTarget;
import java.util.concurrent.ExecutionException;

/**
 * Created by neuro on 24-05-2016.
 */
public class ImageLoader {

  static {
    //not being use
    GlideBuilder builder = new GlideBuilder(Application.getContext());
    builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
  }

  public static void loadScreenshotToThumb(String url, String orientation,
      @DrawableRes int loadingPlaceHolder, ImageView imageView) {
    Glide.with(Application.getContext())
        .load(AptoideUtils.IconSizeU.screenshotToThumb(url, orientation))
        .placeholder(loadingPlaceHolder)
        .into(imageView);
  }

  public static void load(String url, @DrawableRes int loadingPlaceHolder, ImageView imageView) {
    Glide.with(Application.getContext()).load(url).placeholder(loadingPlaceHolder).into(imageView);
  }

  public static void load(String url, ImageView imageView) {
    Glide.with(Application.getContext())
        .load(AptoideUtils.IconSizeU.getNewImageUrl(url))
        .into(imageView);
  }

  /**
   * Loads a Drawable resource from the app bundle.
   *
   * @param drawableId drawable id
   * @return {@link Drawable} with the passing drawable id or null if id = 0
   */
  public static Drawable load(@DrawableRes int drawableId) {
    if (drawableId == 0) {
      return null;
    }
    Context ctx = Application.getContext();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return ctx.getResources().getDrawable(drawableId, ctx.getTheme());
    } else {
      return ctx.getResources().getDrawable(drawableId);
    }
  }

  public static void load(@DrawableRes int drawableId, ImageView imageView) {
    Glide.with(Application.getContext()).load(drawableId).into(imageView);
  }

  /**
   * Mutates URL to append "_50x50" to load an avatar image from an image URL.
   *
   * @param url original image URL
   * @param imageView destination container for the image
   * @param placeHolderDrawableId placeholder while the image is loading or when is not loaded
   */
  public static void loadWithCircleTransformAndPlaceHolderAvatarSize(String url,
      ImageView imageView, @DrawableRes int placeHolderDrawableId) {
    loadWithCircleTransformAndPlaceHolder(AptoideUtils.IconSizeU.generateStringAvatar(url),
        imageView, placeHolderDrawableId);
  }

  public static void loadWithCircleTransformAndPlaceHolder(String url, ImageView imageView,
      @DrawableRes int placeHolderDrawableId) {
    Glide.with(Application.getContext())
        .load(url)
        .transform(new CircleTransform(Application.getContext()))
        .placeholder(placeHolderDrawableId)
        .into(imageView);
  }

  public static void loadWithCircleTransform(@DrawableRes int drawableId, ImageView imageView) {
    Glide.with(Application.getContext())
        .fromResource()
        .load(drawableId)
        .transform(new CircleTransform(Application.getContext()))
        .into(imageView);
  }

  public static void loadWithCircleTransform(String url, ImageView imageView) {
    Glide.with(Application.getContext())
        .load(AptoideUtils.IconSizeU.generateSizeStoreString(url))
        .transform(new CircleTransform(Application.getContext()))
        .into(imageView);
  }

  public static void loadWithCircleTransform(Uri url, ImageView imageView) {
    Glide.with(Application.getContext())
        .load(url.toString())
        .transform(new CircleTransform(Application.getContext()))
        .into(imageView);
  }

  public static void loadWithShadowCircleTransform(String url, ImageView imageView) {
    Glide.with(Application.getContext())
        .load(url)
        .transform(new ShadowCircleTransformation(Application.getContext(), imageView))
        .into(imageView);
  }

  public static void loadWithShadowCircleTransform(@DrawableRes int drawableId,
      ImageView imageView) {
    Glide.with(Application.getContext())
        .fromResource()
        .load(drawableId)
        .transform(new ShadowCircleTransformation(Application.getContext(), imageView))
        .into(imageView);
  }

  public static void loadImageToNotification(NotificationTarget notificationTarget, String url) {
    Glide.with(Application.getContext().getApplicationContext())
        .load(AptoideUtils.IconSizeU.generateStringNotification(url))
        .asBitmap()
        .into(notificationTarget);
  }

  @WorkerThread public static @Nullable Bitmap loadBitmap(Context context, String apkIconPath) {
    try {
      return Glide.
          with(context).
          load(apkIconPath).
          asBitmap().
          into(-1, -1). // full size
          get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    return null;
  }
}
