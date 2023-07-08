package ru.example.instruments.out.ws.helper;

import ru.example.instruments.model.Rating;

import java.util.Map;

import static ru.example.instruments.model.Constant.InstrumentFields.*;
import static ru.example.instruments.out.ws.helper.FieldParser.getStringFieldValue;


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
