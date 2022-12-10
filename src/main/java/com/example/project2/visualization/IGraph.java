package com.example.project2.visualization;

import com.example.project2.INode;

import java.math.BigDecimal;
import java.util.List;

public interface IGraph {
    /**
     * Method getNodes is returns a list of all the nodes in this graph.
     *
     * @return List of INodes
     */
    List<INode> getNodes();

    /**
     * Method getStartNodes gets the start nodes for the graph.
     * There may be multiple start nodes in a graph.
     *
     * @return start INode
     */
    List<INode> getStartNodes();

    /**
     * Method getNodeCount gets the number of nodes in this graph.
     *
     * @return an int for the number of nodes.
     */
    int getNodeCount();

    /**
     * Returns the theoretical upper bound on the number of schedules that could be searched for this graph
     * @return logged double of the result
     */
    double getSchedulesUpperBoundLog();

    /**
     * Returns the theoretical upper bound on the number of schedules that could be searched for this graph
     * @return BigInteger of the result
     */
    BigDecimal getSchedulesUpperBound();

}
