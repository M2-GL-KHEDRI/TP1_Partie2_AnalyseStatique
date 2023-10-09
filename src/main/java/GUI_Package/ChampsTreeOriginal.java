package GUI_Package;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChampsTreeOriginal extends JPanel {

    static Object parent;
    static mxGraph graph;
    mxCompactTreeLayout layout;
    public ChampsTreeOriginal() {
        dess();
    }

    void dess() {

        threading();

        for (Method eths : GUI.listeMethods) {
            graph = new mxGraph();
            parent = graph.getDefaultParent();
            layout = new mxCompactTreeLayout(graph, true);
            layout.setLevelDistance(30);
            layout.setNodeDistance(10);
            layout.setResizeParent(true);

            graph.getModel().beginUpdate();

            this.setSize(1000, 1000);

            try {
                Object root = graph.insertVertex(parent, "r" + eths.getId(), eths.getName(), 0, 0, 100, 80);
                for (int i = 0; i < eths.getAllCalls().size(); i++) {
                    Method call = eths.getAllCalls().get(i);
                    draw(call, root);
                }
                layout.execute(parent);

            } finally{
                graph.getModel().endUpdate();
            }

            graph.addListener(mxEvent.FOLD_CELLS, new mxIEventListener() {

                @Override
                public void invoke(Object sender, mxEventObject evt) {
                    layout.execute(graph.getDefaultParent());
                }
            });
            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            //graphComponent.setSize(1000, 1000);
            add(graphComponent);
        }

    }


    void revalid(){
        removeAll();
        dess();
    }

    public void draw(Method parentN, Object DadGraph) {
        int fnamebreDeson = parentN.getAllCalls().size();
        Object root = graph.insertVertex(parent, "r"+parentN.getId(), parentN.getName(), 0, 0, 100, 80);
        graph.insertEdge(parent, null, "", DadGraph, root);
        for (int i = 0; i < parentN.getAllCalls().size(); i++) {
            Method call = parentN.getAllCalls().get(i);
            draw(call, root);
        }
    }

    static String id;
    public void threading() {

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Object cells = graph.getSelectionCell();


                    repaint();
                    revalidate();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ChampsTreeOriginal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        th.start();
    }
}
