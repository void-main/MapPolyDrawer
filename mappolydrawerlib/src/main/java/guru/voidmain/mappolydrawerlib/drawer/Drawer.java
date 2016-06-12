package guru.voidmain.mappolydrawerlib.drawer;

import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;

/**
 * Created by voidmain on 16/6/12.
 */
public class Drawer {
    public static final String TAG = Drawer.class.getCanonicalName();

    protected IDrawableMap mDrawableMap;

    public Drawer(IDrawableMap drawableMap) {
        mDrawableMap = drawableMap;
    }
}
