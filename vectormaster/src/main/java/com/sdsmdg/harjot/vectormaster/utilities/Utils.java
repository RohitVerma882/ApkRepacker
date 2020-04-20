package com.sdsmdg.harjot.vectormaster.utilities;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.sdsmdg.harjot.vectormaster.DefaultValues;

import java.lang.reflect.Field;

public class Utils {

    public static int getColorFromString(String value, boolean useLightTheme) {

        if (value.length() == 4) {
            return Color.parseColor("#" + value.charAt(1) + value.charAt(1) + value.charAt(2) + value.charAt(2) + value.charAt(3) + value.charAt(3));
        } else if (value.length() == 7 || value.length() == 9) {
            return Color.parseColor(value);
        } else if (value.length() == 2) {
            return Color.parseColor("#" + value.charAt(1) + value.charAt(1) + value.charAt(1) + value.charAt(1) + value.charAt(1) + value.charAt(1) + value.charAt(1) + value.charAt(1));
        } else if (value.startsWith("#")) {
            return Color.parseColor(value);
        } else if (value.startsWith("@android:color/")) {
            Object color = getAndroidColor("android.R$color", value.substring(15));
            if (color != null) {
                return (Integer) color;
            }
        } else {
            return useLightTheme ? DefaultValues.PATH_FILL_COLOR_BLACK : DefaultValues.PATH_FILL_COLOR_WHITE;
        }
        return useLightTheme ? DefaultValues.PATH_FILL_COLOR_BLACK : DefaultValues.PATH_FILL_COLOR_WHITE;
    }

    public static Path.FillType getFillTypeFromString(String value) {
        Path.FillType fillType = Path.FillType.WINDING;
        if (value.equals("1")) {
            fillType = Path.FillType.EVEN_ODD;
        }
        return fillType;
    }

    public static Paint.Cap getLineCapFromString(String value) {
        switch (value) {
            case "0":
                return Paint.Cap.BUTT;
            case "1":
                return Paint.Cap.ROUND;
            case "2":
                return Paint.Cap.SQUARE;
            default:
                return Paint.Cap.BUTT;
        }
    }

    public static Paint.Join getLineJoinFromString(String value) {
        switch (value) {
            case "0":
                return Paint.Join.MITER;
            case "1":
                return Paint.Join.ROUND;
            case "2":
                return Paint.Join.BEVEL;
            default:
                return Paint.Join.MITER;
        }
    }

    public static int getAlphaFromFloat(float value) {
        int newValue = (int) (255 * value);
        return Math.min(255, newValue);
    }

    public static float getAlphaFromInt(int value) {
        return (((float) value) / 255.0f);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float getFloatFromDimensionString(String value) {
        if (value.contains("dip"))
            return Float.parseFloat(value.substring(0, value.length() - 3));
        else
            return Float.parseFloat(value.substring(0, value.length() - 2));
    }

    public static boolean isEqual(Object a, Object b) {
        return a == null && b == null || !(a == null || b == null) && a.equals(b);
    }

    public static Object getAndroidColor(String clazz, String colorName) {
        Field declaratedField;
        try {
            declaratedField = Class.forName(clazz).getField(colorName);
            declaratedField.setAccessible(true);
            return declaratedField.get(null);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
