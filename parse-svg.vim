function! SvgToVector(fileName)
    let number = '\(-\?[0-9]\+[0-9\.]*e[-+][0-9]\+[0-9\.]*\|-\?[0-9]\+[0-9\.]*\)'
    let name = substitute(a:fileName, '.*\/', '', "gI")
    let name = substitute(name, '\..*', '', "gI")
    let package = substitute(a:fileName, '\/[^\/]\+.svg', '', "gI")
    let package = substitute(package, '.*\/java\/com\/', 'com.', "gI")
    let package = substitute(package, '\/', '.', "gI")
    exec '%s/ *\n *\([a-z]\)/\1/g'
    exec '%s/ *</</g'
    let l = 1
    for line in getline(1,"$")
        if line =~ '<svg.*'
          let line = substitute(line, ' *xmlns="[^"]*" *', '', "gI")
          let line = substitute(line, ' *xmlns:svg="[^"]*" *', '', "gI")
          let line = substitute(line, ' *id="[^"]*" *', '', "gI")
          let line = substitute(line, ' *version="[^"]*" *', '', "gI")
          let line = substitute(line, '<svg.*>', 'package '.package.';;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.PathFillType;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.SolidColor;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.StrokeCap;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.StrokeJoin;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.vector.ImageVector;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.graphics.vector.path;\0', "gI")
          let line = substitute(line, '<svg.*>', 'import androidx.compose.ui.unit.dp;;\0', "gI")
          let line = substitute(line, '<svg *\(.*\) *>', 'val '.name.': ImageVector;  get() = cached ?: ImageVector.Builder(;    name = "'.name.'",;\1  ).apply {', "gI")
          let line = substitute(line, ' *width="'.number.'"', '    defaultWidth = \1.dp,;', "gI")
          let line = substitute(line, ' *height="'.number.'"', '    defaultHeight = \1.dp,;', "gI")
          let line = substitute(line, ' *viewBox="'.number.' '.number.' '.number.' '.number.'"', '    viewportWidth = \3f,;    viewportHeight = \4f,;', "gI")
          let line = substitute(line, ' *fill="\([^"]*\)"', '    tintColor = SolidColor("\1"),;', "gI")
        elseif line =~ '<\/svg.*'
          let line = substitute(line, '<\/svg>', '  \}\.build().also { cached = it };;private var cached: ImageVector? = null;', "gI")
        elseif line =~ '<path.*'
          let line = substitute(line, ' *id="[^"]*" *', '', "gI")
          let line = substitute(line, '<path\(.*\)d="\([^"]*\)"\(.*\)\/>', '    path(;\1\3    ) {;\2    }', "gI")
          let line = substitute(line, ' *fill="\([^"]*\)"', '      fill = SolidColor("\1"),;', "gI")
          let line = substitute(line, ' *fill-rule="evenodd"', '      pathFillType = PathFillType.EvenOdd,;', "gI")
          let line = substitute(line, ' *fill-rule="nonzero"', '      pathFillType = PathFillType.NonZero,;', "gI")
          let line = substitute(line, ' *fill-opacity="'.number.'"', '      fillAlpha = \1f,;', "gI")
          let line = substitute(line, ' *clip-rule="\([^"]*\)"', '', "gI")
          let line = substitute(line, ' *opacity="'.number.'"', '      fillAlpha = \1f,;', "gI")
          let line = substitute(line, ' *stroke="\([^"]*\)"', '      stroke = SolidColor("\1"),;', "gI")
          let line = substitute(line, ' *stroke-opacity="'.number.'"', '      strokeAlpha = \1f,;', "gI")
          let line = substitute(line, ' *stroke-width="'.number.'"', '      strokeLineWidth = \1f,;', "gI")
          let line = substitute(line, ' *stroke-linecap="butt"', '      strokeLineCap = StrokeCap.Butt,;', "gI")
          let line = substitute(line, ' *stroke-linecap="round"', '      strokeLineCap = StrokeCap.Round,;', "gI")
          let line = substitute(line, ' *stroke-linecap="square"', '      strokeLineCap = StrokeCap.Square,;', "gI")
          let line = substitute(line, ' *stroke-linejoin="miter"', '      strokeLineJoin = StrokeJoin.Miter,;', "gI")
          let line = substitute(line, ' *stroke-linejoin="round"', '      strokeLineJoin = StrokeJoin.Round,;', "gI")
          let line = substitute(line, ' *stroke-linejoin="bevel"', '      strokeLineJoin = StrokeJoin.Bevel,;', "gI")
          let line = substitute(line, ' *stroke-miterlimit="'.number.'"', '      strokeLineMiter = \1f,;', "gI")
          let line = substitute(line, ' *M *'.number.'[ ,]*'.number.'', '      moveTo(\1f, \2f);', "gI")
          let line = substitute(line, ' *m *'.number.'[ ,]*'.number.'', '      moveToRelative(\1f, \2f);', "gI")
          let line = substitute(line, ' *L *'.number.'[ ,]*'.number.'', '      lineTo(\1f, \2f);', "gI")
          let line = substitute(line, ' *l *'.number.'[ ,]*'.number.'', '      lineToRelative(\1f, \2f);', "gI")
          let line = substitute(line, ' *H *'.number.'', '      horizontalLineTo(\1f);', "gI")
          let line = substitute(line, ' *h *'.number.'', '      horizontalLineToRelative(\1f);', "gI")
          let line = substitute(line, ' *V *'.number.'', '      verticalLineTo(\1f);', "gI")
          let line = substitute(line, ' *v *'.number.'', '      verticalLineToRelative(\1f);', "gI")
          let line = substitute(line, ' *C *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      curveTo(\1f, \2f, \3f, \4f, \5f, \6f);', "gI")
          let line = substitute(line, ' *c *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      curveToRelative(\1f, \2f, \3f, \4f, \5f, \6f);', "gI")
          let line = substitute(line, ' *S *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      reflectiveCurveTo(\1f, \2f, \3f, \4f);', "gI")
          let line = substitute(line, ' *s *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      reflectiveCurveToRelative(\1f, \2f, \3f, \4f);', "gI")
          let line = substitute(line, ' *Q *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      quadTo(\1f, \2f, \3f, \4f);', "gI")
          let line = substitute(line, ' *q *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*'.number.'', '      quadToRelative(\1f, \2f, \3f, \4f);', "gI")
          let line = substitute(line, ' *T *'.number.'[ ,]*'.number.'', '      reflectiveQuadTo(\1f, \2f);', "gI")
          let line = substitute(line, ' *t *'.number.'[ ,]*'.number.'', '      reflectiveQuadToRelative(\1f, \2f);', "gI")
          let line = substitute(line, ' *C *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*0[ ,]*0[ ,]*'.number.'[ ,]*'.number.'', '      arcTo(\1f, \2f, \3f, false, false, \6f, \7f);', "gI")
          let line = substitute(line, ' *C *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*0[ ,]*1[ ,]*'.number.'[ ,]*'.number.'', '      arcTo(\1f, \2f, \3f, false, true, \6f, \7f);', "gI")
          let line = substitute(line, ' *C *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*1[ ,]*0[ ,]*'.number.'[ ,]*'.number.'', '      arcTo(\1f, \2f, \3f, true, false, \6f, \7f);', "gI")
          let line = substitute(line, ' *C *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*1[ ,]*1[ ,]*'.number.'[ ,]*'.number.'', '      arcTo(\1f, \2f, \3f, true, true, \6f, \7f);', "gI")
          let line = substitute(line, ' *c *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*0[ ,]*0[ ,]*'.number.'[ ,]*'.number.'', '      arcToRelative(\1f, \2f, \3f, false, false, \6f, \7f);', "gI")
          let line = substitute(line, ' *c *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*0[ ,]*1[ ,]*'.number.'[ ,]*'.number.'', '      arcToRelative(\1f, \2f, \3f, false, true, \6f, \7f);', "gI")
          let line = substitute(line, ' *c *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*1[ ,]*0[ ,]*'.number.'[ ,]*'.number.'', '      arcToRelative(\1f, \2f, \3f, true, false, \6f, \7f);', "gI")
          let line = substitute(line, ' *c *'.number.'[ ,]*'.number.'[ ,]*'.number.'[ ,]*1[ ,]*1[ ,]*'.number.'[ ,]*'.number.'', '      arcToRelative(\1f, \2f, \3f, true, true, \6f, \7f);', "gI")
          let line = substitute(line, '; *[Zz]', ';      close();', "gI")
        else
          let line = '//'.line
        endif
        call setline(l, line)
        let l = l + 1
    endfor
    exec '%s/;/\r/g'
endfunction
