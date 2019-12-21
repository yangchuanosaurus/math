package arch.lifecycle.model;

/**
 * ARCH LIFECYCLE MODEL
 * lifecycle related to the lifecycle, which defined by not only the android lifecycle (ACTIVITY / FRAGMENT),
 * but also customize lifecycle such as page flow, model flow
 *
 *
 *
 * lifecycle define below phases
 * - start
 * - end
 *
 * Activity Lifecycle
 * - onActivityCreated, the model will created
 * - onActivityDestroyed, the model will destroy
 *
 * Fragment Lifecycle
 * - onFragmentCreated, the model not related {@link android.content.Context} will created
 * - onFragmentContextCreated, the model related {@link android.content.Context} will created
 * - onFragmentDestroyed, the model will destroy
 *
 * Page Flow Lifecycle
 * - Page Flow Defined (A -> B -> C)
 *  - On Page A created, the model will created
 *  - On Page A destroyed and goto Page B, the model will retain
 *  - On Page A destroyed and goto Other Pages (Not Page B), the model will destroy
 *  - On Page B created, the model will
 *
 * Model Flow Lifecycle
 *
 * lifecycle related to the lifecycle, which defined by a String lifecycle
 * */
public class ArchLifecycleModel {
}
