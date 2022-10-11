package com.zhou.cache.guava;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

/**
 *  ——————————————————————————————————————————————————————————————————
 *  ImmutableList 的特性：
 *  1、它是不可变的；
 *  2、无法添加，修改和删除其内部的元素；
 *  3、如果尝试执行添加/删除/更新操作，则会得到UnsupportedOperationException。
 *  ——————————————————————————————————————————————————————————————————
 *
 * @author zhouyuanke
 * @date 2022/9/23 12:45
 */
public class MockDb {
    private static final String CITY = "city";

    public static List<CityInfo> getCityListFromDb(String key) {
        if (!CITY.equals(key)) {
            return Collections.emptyList();
        }
        System.out.println("getting " + key + " from DB, please waiting ……");
        CityInfo cityInfo1 = new CityInfo(1, "上海");
        CityInfo cityInfo2 = new CityInfo(2, "北京");
        CityInfo cityInfo3 = new CityInfo(3, "南京");
        List<CityInfo> cities = ImmutableList.of(cityInfo1, cityInfo2, cityInfo3);
        return cities;
    }

    public static List<CityInfo> getCityListFromTail(String key) {
        if (!CITY.equals(key)) {
            return Collections.emptyList();
        }
        System.out.println("getting " + key + " from Tail, please waiting ……");
        CityInfo cityInfo1 = new CityInfo(1, "SHANGHAI");
        CityInfo cityInfo2 = new CityInfo(2, "BEIJING");
        CityInfo cityInfo3 = new CityInfo(3, "NANJING");
        List<CityInfo> cities = ImmutableList.of(cityInfo1, cityInfo2, cityInfo3);
        return cities;
    }
}
