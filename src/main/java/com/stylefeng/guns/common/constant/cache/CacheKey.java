package com.stylefeng.guns.common.constant.cache;

/**
 * 缓存的key集合
 *
 * @author fengshuonan
 * @date 2017-04-25 9:37
 */
public interface CacheKey {

    /**
     * ConstantFactory中的缓存
     */
    String ROLES_NAME = "roles_name_";

    String SINGLE_ROLE_NAME = "single_role_name_";

    String SINGLE_ROLE_TIP = "single_role_tip_";

    String DEPT_NAME = "dept_name_";

    String OTHER_INFO = "other_info_";

    String GAME_AREA_LIST = "game_area_list";

    String GAME_AREA_ = "game_area_";

    String GAME_TYPE_LIST = "game_type_list";

    String GAME_BILI = "game_bili_";

    String GAME_BILI_LIST = "game_bili_list";

    String GAME_BILI_TEXT = "game_bili_text";

    String GAME_LAST_OPEN_PREFIX = "gameLastOpen_";

    String GAME_LATELY_OPEN_PREFIX = "gameLatelyOpen_";

    String ROOM_INFO = "room_info_";

    String ROOM_INFO_AREA_LIST = "room_info_area_list_";

    String ROBOT_INFO_PREFIX = "robotInfo_";


    String USER_TOKEN = "user_token_";

    /**
     * 缓存：开奖时间缓存
     */
    String STOP_CHOOSE_TIME_CACHE = "stopChooseTimeCache_";
}
