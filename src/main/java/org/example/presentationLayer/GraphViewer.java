package org.example.presentationLayer;

import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.Source;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.GraphRenderer;
import org.graphstream.ui.view.Viewer;

public class GraphViewer extends Viewer{
    @Override
    public String getDefaultID() {
        return null;
    }

    @Override
    public void init(GraphicGraph graph, ProxyPipe ppipe, Source source) {

    }

    @Override
    public void close() {

    }

    @Override
    public GraphRenderer<?, ?> newDefaultGraphRenderer() {
        return null;
    }
}
