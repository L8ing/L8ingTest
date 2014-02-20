/*
 *    Copyright (c) 2013 ADTEC
 *    All rights reserved
 *
 *    本程序为自由软件；您可依据自由软件基金会所发表的GNU通用公共授权条款规定，就本程序再为发布与／或修改；无论您依据的是本授权的第二版或（您自行选择的）任一日后发行的版本。
 *    本程序是基于使用目的而加以发布，然而不负任何担保责任；亦无对适售性或特定目的适用性所为的默示性担保。详情请参照GNU通用公共授权。
 */

package test.blockingqueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>TestQueue</p>
 * <p>类的详细说明</p>
 * <p>Copyright: Copyright (c) 2013</p> 
 * <p>Company: 北京先进数通信息技术股份公司</p> 
 * @author  Administrator
 * @version 1.0 2014年1月16日 Administrator
 * <p>          修改者姓名 修改内容说明</p>
 * @see     参考类1
 */
public class TestQueue {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BlockingQueue<byte[]> bq = new BlockingQueue<byte[]>() {

            @Override
            public byte[] remove() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public byte[] poll() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public byte[] element() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public byte[] peek() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Iterator<byte[]> iterator() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object[] toArray() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T> T[] toArray(T[] a) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends byte[]> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean add(byte[] e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean offer(byte[] e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void put(byte[] e) throws InterruptedException {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean offer(byte[] e, long timeout, TimeUnit unit)
                    throws InterruptedException {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public byte[] take() throws InterruptedException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public byte[] poll(long timeout, TimeUnit unit)
                    throws InterruptedException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int remainingCapacity() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean remove(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public int drainTo(Collection<? super byte[]> c) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int drainTo(Collection<? super byte[]> c, int maxElements) {
                // TODO Auto-generated method stub
                return 0;
            }
        };
    }

}
