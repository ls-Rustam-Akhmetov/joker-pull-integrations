package ru.example.bloomberg.out.ws.helper;

import ru.example.bloomberg.model.instrument.Rating;

import java.util.Map;

import static ru.example.bloomberg.model.Constant.InstrumentFields.*;
import static ru.example.bloomberg.out.ws.helper.FieldParser.getStringFieldValue;

public class RatingParser {

    private RatingParser() {
    }

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
