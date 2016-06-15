package guru.voidmain.mappolydrawerlib.virtualoverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Computes overlay diff from 2 overlay containers
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayDiffComputer {

    public static VirtualOverlayDiffContainer computerDiffs(VirtualOverlayContainer newOC, VirtualOverlayContainer oldOC) {
        List<BaseVirtualOverlay> newOverlays = newOC.getVirtualOverlays();
        List<BaseVirtualOverlay> oldOverlays = oldOC.getVirtualOverlays();

        List<BaseVirtualOverlay> addedOverlays = new ArrayList<>();
        List<BaseVirtualOverlay> deletedOverlays = new ArrayList<>();
        List<BaseVirtualOverlay> updatedOverlays = new ArrayList<>();

        for (BaseVirtualOverlay overlay :
                newOverlays) {
            int overlayInOldOverlaysIndex = oldOverlays.indexOf(overlay);
            if (overlayInOldOverlaysIndex != -1) { // found in new overlays
                BaseVirtualOverlay oldOverlay = oldOverlays.get(overlayInOldOverlaysIndex);
                if (!overlay.isContentEquals(oldOverlay)) {
                    updatedOverlays.add(overlay);
                }
            } else { // not found in old overlays
                addedOverlays.add(overlay);
            }
        }

        for (BaseVirtualOverlay overlay:
                oldOverlays) {
            int overlayInNewOverlaysIndex = newOverlays.indexOf(overlay);
            if (overlayInNewOverlaysIndex == -1) { // not found in new overlays
                deletedOverlays.add(overlay);
            }
        }

        return new VirtualOverlayDiffContainer(
                new VirtualOverlayContainer(addedOverlays),
                new VirtualOverlayContainer(deletedOverlays),
                new VirtualOverlayContainer(updatedOverlays));
    }
}
