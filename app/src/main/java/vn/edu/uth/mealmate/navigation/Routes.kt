package vn.edu.uth.mealmate.navigation

object Routes {
    const val ONBOARDING = "onboarding"            // S01
    const val REGISTER = "register"                // S02
    const val SETUP = "setup"                      // S03
    const val HOME = "home"                        // S04
    const val CREATE_PLAN = "create_plan"          // S05
    const val GENERATING = "generating"            // S06
    const val MEAL_PLAN = "meal_plan"              // S07
    const val MEAL_DETAIL = "meal_detail/{mealId}" // S08
    const val SHOPPING = "shopping"                // S09
    const val PANTRY = "pantry"                    // S10
    const val NUTRITION = "nutrition"              // S11
    const val FAVORITES = "favorites"              // S12
    const val HISTORY = "history"                  // S12b
    const val NOTIFICATIONS = "notifications"      // S13
    const val NOTIFICATION_SETTINGS = "notification_settings" // S14
    const val COST_STATS = "cost_stats"            // S15
    const val PROFILE = "profile"                  // S16
    const val IMAGE_CREDITS = "image_credits"      // Nguồn hình ảnh

    fun mealDetail(mealId: String) = "meal_detail/$mealId"
}
