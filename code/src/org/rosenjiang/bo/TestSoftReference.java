package org.rosenjiang.bo;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;
import org.rosenjiang.bo.Pilot;
import org.rosenjiang.bo.SoftRefedPilot;

public class TestSoftReference {

    public static void main(String[] args) {
        soft();
    }
    
    static void soft(){
        Map<Integer, SoftRefedPilot> map = new HashMap<Integer, SoftRefedPilot>();
        ReferenceQueue<Pilot> queue = new ReferenceQueue<Pilot>();
        int i = 0;
        while (i < 10000000) {
            Pilot p = new Pilot();
            map.put(i, new SoftRefedPilot(i, p, queue));
            //p = null;
            SoftRefedPilot pollref = (SoftRefedPilot) queue.poll();
            if (pollref != null) {//�ҳ��������û��յĶ���
                //��keyΪ��־����map���Ƴ�
                map.remove(pollref.key);
            }
            i++;
        }
        System.out.println("done");
    }
}