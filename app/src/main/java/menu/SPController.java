package menu;

/**
 * @author JayZhao
 * @date 2020/5/28
 **/
public class SPController {
    enum id{
        KEY_MOUSE_MODE,
        KEY_ENABLE_VIBRATEABLE,
        KEY_GYROSCOPE_MODE,
        KEY_GYROSCOPE_SENSITIVITY,
        KEY_IS_AUTO_QUALITY,
        KEY_ENABEL_REAL_TIME_MONITORING,
        KEY_ENABEL_STRETCH_VIDEO
    }

    public static SPController getInstance(){
        return new SPController();
    }

    public int getBitrate(){
        return -1;
    }

    public void setIntValue(id id, int Value){

    }

    public int getIntValue(id id, int defValue){
        return defValue;
    }

    public boolean getBooleanValue(id id, boolean defValue){
        return defValue;
    }

    public void setBooleanValue(id id, boolean defValue){
    }

    public void setMouseSpeed(int process){}
}
