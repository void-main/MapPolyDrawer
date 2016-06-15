package guru.voidmain.mappolydrawerlib.virtualoverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayContainer extends BaseVirtualOverlay {
    protected List<BaseVirtualOverlay> mVirtualOverlays;

    public VirtualOverlayContainer() {
        this(new ArrayList<BaseVirtualOverlay>());
    }

    public VirtualOverlayContainer(List<BaseVirtualOverlay> virtualOverlays) {
        mVirtualOverlays = virtualOverlays;
    }

    public List<BaseVirtualOverlay> getVirtualOverlays() {
        return mVirtualOverlays;
    }

    public void setVirtualOverlays(List<BaseVirtualOverlay> virtualOverlays) {
        mVirtualOverlays = virtualOverlays;
    }

    public void addVirtualOverlay(BaseVirtualOverlay virtualOverlay) {
        mVirtualOverlays.add(virtualOverlay);
    }
}
