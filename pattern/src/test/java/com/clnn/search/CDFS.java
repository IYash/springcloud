package com.clnn.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 深度优先遍历，采用遍历第一个节点的方式进行遍历
 */
public class CDFS {

    //用6个节点的数据进行demo
    static boolean[] visited = new boolean[]{false,false,false,false,false,false,false};
    //使用stack用于记录访问的次序？使用递归已经实现次序的记录过程
    //回溯的动作怎么触发？
    static List<Integer> lastIndexs = new ArrayList<>();

    public static void deep(int lastIndex,int index , Graph graph){
        System.out.println("------访问顶点为:"+index);
        //获取图的邻接关系
        int[] vertex = graph.getMatrixVertex(index);
        lastIndexs.add(index);
        //寻找满足访问的点
        int nextIndex = -1;
        int i;
        for(i=0;i<vertex.length;i++){
            if(vertex[i]>0 && vertex[i]<graph.MAX && !visited[i]){
                nextIndex = i;
                lastIndex = index;
                break;
            }
        }
        visited[index]=true;
        //如果nextIndex == -1，则说明已经不存在下一个，需要返回上一个节点,有bug继续思考哦
        //todo
        if( nextIndex == -1) nextIndex = findLastIndex(index);
        //标记节点已遍历
        if(nextIndex > 0)//0这个节点作为最终的回溯点
        deep(lastIndex,nextIndex,graph);
    }

    public static void deep(int index , Graph graph){
        System.out.println("------访问顶点为:"+index);
        //获取图的邻接关系
        int[] vertex = graph.getMatrixVertex(index);
        //寻找满足访问的点
        int nextIndex = -1;
        int i;
        for(i=0;i<vertex.length;i++){
            if(vertex[i]>0 && vertex[i]<graph.MAX && !visited[i]){
                nextIndex = i;
                lastIndexs.add(index);
                break;
            }
        }
        visited[index]=true;
        //如果nextIndex == -1，则说明已经不存在下一个，需要返回上一个节点,有bug继续思考哦
        //todo
        if( nextIndex == -1) nextIndex = findLastIndex();
        //标记节点已遍历
        if(nextIndex > 0)//0这个节点作为最终的回溯点
            deep(nextIndex,graph);
    }

    public static int findLastIndex(int index){
        for(int i =0;i<lastIndexs.size();i++){
            if(lastIndexs.get(i) == index) return lastIndexs.get(i-1);
        }
        return 0;
    }
    public static int findLastIndex(){
        return lastIndexs.remove(lastIndexs.size()-1);
    }

    public static void main(String[] args) {
        Graph graph = new Graph().demoGraph();
        //deep(0,0,graph);
        deep(0,graph);
    }
}
