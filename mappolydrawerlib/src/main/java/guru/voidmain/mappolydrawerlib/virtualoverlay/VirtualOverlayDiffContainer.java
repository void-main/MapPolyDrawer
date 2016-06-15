package guru.voidmain.mappolydrawerlib.virtualoverlay;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayDiffContainer {
    protected VirtualOverlayContainer mAddedVirtualOverlays;
    protected VirtualOverlayContainer mRemovedVirtualOverlays;
    protected VirtualOverlayContainer mUpdatedVirtualOverlays;

    public VirtualOverlayDiffContainer(VirtualOverlayContainer addedVirtualOverlays,
                                       VirtualOverlayContainer removedVirtualOverlays,
                                       VirtualOverlayContainer updatedVirtualOverlays) {
        this.mAddedVirtualOverlays = addedVirtualOverlays;
        this.mRemovedVirtualOverlays = removedVirtualOverlays;
        this.mUpdatedVirtualOverlays = updatedVirtualOverlays;
    }

    public VirtualOverlayContainer getAddedVirtualOverlays() {
        return mAddedVirtualOverlays;
    }

    public void setAddedVirtualOverlays(VirtualOverlayContainer addedVirtualOverlays) {
        this.mAddedVirtualOverlays = addedVirtualOverlays;
    }

    public VirtualOverlayContainer getRemovedVirtualOverlays() {
        return mRemovedVirtualOverlays;
    }

    public void setRemovedVirtualOverlays(VirtualOverlayContainer removedVirtualOverlays) {
        this.mRemovedVirtualOverlays = removedVirtualOverlays;
    }

    public VirtualOverlayContainer getUpdatedVirtualOverlays() {
        return mUpdatedVirtualOverlays;
    }

    public void setUpdatedVirtualOverlays(VirtualOverlayContainer updatedVirtualOverlays) {
        this.mUpdatedVirtualOverlays = updatedVirtualOverlays;
    }
}
