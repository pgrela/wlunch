package com.pa2013;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class mro {
    public static void main(String[] argv){
        new mro().start();
    }
    public static int MAX=1000000001;
    PriorityQueue<Event> queue = new PriorityQueue<Event>();
    Node root;
    TreeMap<Integer,Drop> drops = new TreeMap<Integer,Drop>();
    public void start(){
        int[] ants={1,3,4,6,7};
        Drop[] drops={new Drop(1,2), new Drop(2,9), new Drop(4,5), new Drop(4,1)};
        String result = doit(ants,drops);
        System.out.println(result);
    }
    public String doit(int[] ants,Drop[] drops){
        root = createNodeBranch(ants,0,ants.length);

        for(Drop drop : drops){
            queue.add(new Event(Event.EventType.DROP,drop));
        }
        while (!queue.isEmpty()){
            Event event =   queue.poll();
            if(event.event== Event.EventType.DRINK)
                drink(event.drop);
            if(event.event== Event.EventType.DROP)
                drop(event.drop);
        }
        StringBuilder stringBuilder = new StringBuilder();
        root.readAll(stringBuilder,0);
        return stringBuilder.toString();

    }

    public void drink(Drop drop) {
        if(!drops.containsKey(drop.x))return;
        Drop toRemove = drops.get(drop.x);
        if(toRemove.eatenFromLeft!=drop.time && toRemove.eatenFromRight!=drop.time)return;
        Map.Entry<Integer, Drop> entry;
        Drop right = null;
        Drop left = null;
        entry = drops.lowerEntry(drop.x);
        if(entry!=null)left=entry.getValue();
        entry = drops.higherEntry(drop.x);
        if(entry!=null)right=entry.getValue();
        persist(left,toRemove,drop.time);
        persist(toRemove,right,drop.time);
        drops.remove(drop.x);
        updateEatenTimes(left,right,drop.time);
    }

    public void drop(Drop drop){
        if(drops.containsKey(drop.x))return;
        Map.Entry<Integer, Drop> entry;
        Drop right = null;
        Drop left = null;
        entry = drops.lowerEntry(drop.x);
        if(entry!=null)left=entry.getValue();
        entry = drops.higherEntry(drop.x);
        if(entry!=null)right=entry.getValue();
        persist(left,right,drop.time);
        Integer firstKnown = root.firstToTheLeftFrom(drop.x+1);
        if(firstKnown!=null && firstKnown ==drop.x)return;
        drops.put(drop.x,drop);
        updateEatenTimes(left, drop, drop.time);
        updateEatenTimes(drop, right, drop.time);
    }

    public void persist(Drop left, Drop right, int time) {
        if(left==null && right==null)return;
        int lastUpdate;
        if(left==null)lastUpdate=right.updateLeft;
        else lastUpdate=left.updateRight;
        int steps = time - lastUpdate;
        if(left==null){
            root.move(0,right.x-1,steps);
        }
        if(right==null){
            root.move(left.x+1,MAX,-steps);
        }
        if(left!=null && right!=null){
            int middle = (right.x+left.x)/2;
            root.move(left.x+1,middle,-steps);
            root.move(middle+1,right.x-1,steps);
        }
        if(left!=null){
            left.updateRight=time;
        }
        if(right!=null){
            right.updateLeft=time;
        }
    }
    public void updateEatenTimes(Drop left, Drop right, int time){
        if(left==null && right==null)return ;
        if(left==null){
            right.updateFromLeft(time,0);
        }
        if(right==null){
            left.updateFromRight(time,MAX);
        }
        if(left!=null && right!=null){
            int leftInnerBound = (left.x+right.x)/2;
            left.updateFromRight(time, leftInnerBound);
            right.updateFromLeft(time, leftInnerBound+1);
        }
    }

    static class Event implements Comparable<Event>{
        @Override
        public int compareTo(Event other) {
            return other.drop.time < this.drop.time ? 1 : (other.drop.time == this.drop.time ? 0 : -1);
        }

        public static enum EventType{
            DROP,DRINK
        }
        public EventType event;
        public Drop drop;

        Event(EventType event, Drop drop) {
            this.event = event;
            this.drop = drop;
        }
    }
    Node createNodeBranch(int[] ants, int begin, int quantity){
        if(quantity==1)return new Node(ants[begin]);
        int quantity2 = quantity/2;
        return new Node(createNodeBranch(ants,begin,quantity2),createNodeBranch(ants,begin+quantity2,
            quantity-quantity2));

    }
    public class Drop implements Comparable<Drop>{
        int time;
        int x;
        int updateLeft;
        int updateRight;
        public int eatenFromLeft;
        public int eatenFromRight;

        public Drop(int time, int x) {
            this.time = time;
            this.x = x;
            updateLeft=updateRight=time;
        }
        @Override
        public int compareTo(Drop other) {
            return other.time < this.time ? 1 : (other.time == this.time ? 0 : -1);
        }
        void updateFromLeft(int time, int bound){
            Integer d=root.firstToTheLeftFrom(x);
            if(d==null){
                eatenFromLeft=MAX;
                return;
            }
            if(d<bound)return;
            int leftTime=x-d;
            int eatenTime = time +leftTime;
            if(eatenTime!=eatenFromLeft){
                queue.add(new Event(Event.EventType.DRINK,new Drop(eatenTime,x)));
                eatenFromLeft=eatenTime;
            }
        }
        void updateFromRight(int time, int bound){
            Integer d=root.firstToTheRightFrom(x);
            if(d==null){
                eatenFromRight=MAX;
                return;
            }
            if(d>bound)return;
            int leftTime=d-x;
            int eatenTime = time +leftTime;
            if(eatenTime!=eatenFromRight){
                queue.add(new Event(Event.EventType.DRINK,new Drop(eatenTime,x)));
                eatenFromRight=eatenTime;
            }
        }
    }
    public class Node{
        public int min;
        public int max;
        public int legacy;
        Node left;
        Node right;
        public Node(int x){
            min=max=x;
            left=null;
            right=null;
        }
        public Node(Node left, Node right){
            this.left=left;
            this.right=right;
            min=left.min;
            max=right.max;
        }
        void move(int from ,int to, int steps){
            if(from<=min && max<=to){
                legacy+=steps;
                min+=steps;
                max+=steps;
                return;
            }
            if(min>to || max<from)return;
            if(left!=null)left.move(from, to, steps);
            if(right!=null)right.move(from, to, steps);
            max=right==null?(left==null?max:left.max+legacy):right.max+legacy;
            min=left==null?(right==null?min:right.min+legacy):left.min+legacy;
        }
        Integer firstToTheLeftFrom(int place){
            if(max<place)return max;
            if(min>=place)return null;
            place-=legacy;
            if(right.min<place)return right.firstToTheLeftFrom(place)+legacy;
            return left.firstToTheLeftFrom(place)+legacy;
        }
        Integer firstToTheRightFrom(int place){
            if(min>place)return min;
            if(max<=place)return null;
            place-=legacy;
            if(left.max>place)return left.firstToTheRightFrom(place)+legacy;
            return right.firstToTheRightFrom(place)+legacy;
        }


        public void readAll(StringBuilder builder, int legacy) {
            if(left!=null)left.readAll(builder,legacy+this.legacy);
            if(right!=null)right.readAll(builder,legacy+this.legacy);
            if(left==null && right==null) builder.append(min+legacy).append(" ");

        }
    }
}
