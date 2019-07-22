package com.jiajiayue.all.regiondrp;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author WangHao
 * @date 2019/7/16 11:29
 */
public class LinkedListDemo {


    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        Node node1 = new Node(1, "李元霸");
        Node node2 = new Node(2, "宇文成都");
        Node node3 = new Node(3, "裴元庆");
        Node node4 = new Node(4, "雄阔海");
       /* singleLinkedList.add(node1);
        singleLinkedList.add(node2);
        singleLinkedList.add(node3);
        singleLinkedList.add(node4);*/
        singleLinkedList.sortAdd(node2);
        singleLinkedList.sortAdd(node1);
        singleLinkedList.sortAdd(node4);
        singleLinkedList.sortAdd(node3);
        singleLinkedList.print();
       /* singleLinkedList.reverse();
        singleLinkedList.print();*/
    }


}

class SingleLinkedList {

    Node head = new Node(null, null);


    public void add(Node node) {
        Node temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = node;
                break;
            }
            temp = temp.next;
        }

    }

    public void sortAdd(Node node) {

        Node temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (node.no < temp.next.no) {
                break;
            }
            temp = temp.next;
        }
        node.next = temp.next;
        temp.next = node;
    }

    public void print() {
        if (this.isEmpty()) {
            System.out.println("链表为空");
        }
        Node temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            System.out.println(temp.next);
            temp = temp.next;
        }
    }

    public Boolean isEmpty() {
        return head.next == null;
    }

    public void reverse() {
        Node cur = head.next;
        Node next = null;
        Node reversehead = new Node();
        while (true) {
            if (cur == null) {
                break;
            }
            next = cur.next;
            cur.next = reversehead.next;
            reversehead.next = cur;
            cur = next;
        }
        head.next = reversehead.next;
    }
}

class Node {

    Integer no;
    String name;
    Node next;

    public Node() {
    }

    public Node(Integer no, String name) {
        this.no = no;
        this.name = name;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "no=" + no + " name=" + name;
    }
}