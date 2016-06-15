package guru.voidmain.mappolydrawerlib.virtualoverlay;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayDiffComputer {

    public static VirtualOverlayDiffContainer computerDiffs(VirtualOverlayContainer newOC, VirtualOverlayContainer oldOC) {
        // TODO actually compute diff instead of return the new one.
        return new VirtualOverlayDiffContainer(newOC, oldOC, new VirtualOverlayContainer());
    }
}
