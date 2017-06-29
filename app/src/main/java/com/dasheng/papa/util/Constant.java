package com.dasheng.papa.util;

public class Constant {

    public static class Api {
        public static final String BASE_URL = "http://118.89.233.35:9090/";
        public static final String BASE_API_URL = BASE_URL + "api/";
        public static final String GET_CONTENT = "get_content";
        public static final String IMG = "img";
        public static final String ZAN = "zan";
        public static final String GET_OTHER = "get_other";
        public static final String SEARCH = "search";
        public static final String SINGLE_PAGE_SIZE = "10";
        public static final String CATEGORY_INFO_LIST = "category_info_list";
        public static final String ZAN_STATUS_ADD = "1";
        public static final String ZAN_STATUS_DEL = "0";
    }

    public static class Intent_Extra {
        public static final String HOME_CATEGORY_TYPE = "home_category_type";
        public static final String CATEGORY_TYPE = "category_type";
        public static final String BEAUTY_PIC = "beauty_pic";
        public static final String RANK_DAY_TYPE = "rank_day_type";
        public static final String VIDEO_DETAIL_INFO = "video_detail_info";
        public static final String HOME_CATEGORY_NAME = "home_category_name";
    }

    public static class Cache {

        public static final String CACHE_HOME_CATEGORY = "home_category_cache";
        public static final String CACHE_CATEGORY_DETAIL = "cache_category_detail";
        public static final String CACHE_BEAUTY = "cache_beauty";
        public static final String CACHE_RANK_LIST = "cache_rank_list";
    }
}
