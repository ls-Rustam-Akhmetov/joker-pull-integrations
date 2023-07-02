package ru.bcs.perseus.bloomberg.out.ws.helper;

import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.BB_COMPOSITE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_FITCH_NO_WATCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_MOODY_NO_WATCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_SP_NO_WATCH;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getStringFieldValue;

import java.util.Map;
import ru.bcs.perseus.bloomberg.model.instrument.Rating;

public class RatingHelper {

  private RatingHelper() {
  }

  /**
   * @should return correct result
   */
  static Rating getRating(Map<String, String> fieldValues) {

    String rtgMoodyNoWatch = getStringFieldValue(fieldValues, RTG_MOODY_NO_WATCH);
    String rtgSpNoWatch = getStringFieldValue(fieldValues, RTG_SP_NO_WATCH);
    String rtgFitchNoWatch = getStringFieldValue(fieldValues, RTG_FITCH_NO_WATCH);
    String bbComposite = getStringFieldValue(fieldValues, BB_COMPOSITE);

    if (rtgMoodyNoWatch == null && rtgSpNoWatch == null && rtgFitchNoWatch == null
        && bbComposite == null) {
      return null;
    }

    return Rating.builder()
        .composite(bbComposite)
        .fitch(rtgFitchNoWatch)
        .sp(rtgSpNoWatch)
        .moody(rtgMoodyNoWatch)
        .build();
  }
}
