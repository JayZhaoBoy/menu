package menu;

/**
 * @author JayZhao
 * @date 2020/5/28
 **/
public class GyroscopeManager {
    //关闭
    public static final int SENSOR_MODE_NONE = 1;
    //模式1
    public static final int SENSOR_MODE_RELATIVE = 2;
    //模式2
    public static final int SENSOR_MODE_RELATIVE_REVERSE = 3;

    public static GyroscopeManager getInstance(){
        return new GyroscopeManager();
    }

    public void setSensorMode(int mode){}

    public void setSensorSensitivity(int sensitivity){}
}
