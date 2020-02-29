package com.clnn.search;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 广度优先遍历，基于队列的特征遍历图,输入根节点，完成整个的遍历路径
 */
public class CBFS {

    //用于存放进入的队列的节点
    static BlockingQueue<Integer> bqb = new ArrayBlockingQueue<Integer>(100);

    static boolean[] visited = new boolean[]{false,false,false,false,false,false,false};

    static Map<Integer,Boolean> existMap = new HashMap<Integer, Boolean>();

    public CBFS() {
    }

    public CBFS(BlockingQueue<Integer> bqb) {
        this.bqb = bqb;
    }

    //执行广度遍历逻辑
    public static void broadcast(int index,Graph graph){
        //取出图中标记为index的顶点
        //bqb.add(index);
        System.out.println("------访问顶点为:"+index);
        //获取图的邻接关系
        int[] vertex = graph.getMatrixVertex(index);
        for(int i=0;i<vertex.length;i++){
            if(vertex[i]>0 && vertex[i]<graph.MAX && !visited[i] && existMap.get(i) == null){
                bqb.add(i);
                existMap.put(i,true);
                System.out.println("------入队顶点为:"+i);
            }
        }
        System.out.println("-----------------------------出队:"+index);
        //顶点遍历结束,出队
        //添加遍历标记
        visited[index] = true;
        //访问下一个顶点
        int next = -1;
        if(bqb.size()>0)
        next = bqb.remove();
        if(next > 0)
        broadcast(next,graph);
    }

    public static void main(String[] args) {
        Graph graph = new Graph().demoGraph();
        //只要给定根节点完成遍历输出
        broadcast(0,graph);
    }
}
