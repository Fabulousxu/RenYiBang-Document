package com.example.renyibang.util;

public class PriceUtil {
    public static long priceConvert(long price)
    {
        return (price == -1 ) ? Long.MAX_VALUE :price;
    }
}
