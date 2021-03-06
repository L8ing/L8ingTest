/*
 *    Copyright (c) 2013 ADTEC
 *    All rights reserved
 *
 *    本程序为自由软件；您可依据自由软件基金会所发表的GNU通用公共授权条款规定，就本程序再为发布与／或修改；无论您依据的是本授权的第二版或（您自行选择的）任一日后发行的版本。
 *    本程序是基于使用目的而加以发布，然而不负任何担保责任；亦无对适售性或特定目的适用性所为的默示性担保。详情请参照GNU通用公共授权。
 */

package test.managementfactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * <p>TestManagementFactory</p>
 * <p>类的详细说明</p>
 * <p>Copyright: Copyright (c) 2013</p> 
 * <p>Company: 北京先进数通信息技术股份公司</p> 
 * @author  Administrator
 * @version 1.0 2014年1月24日 Administrator
 * <p>          修改者姓名 修改内容说明</p>
 * @see     参考类1
 */
public class TestManagementFactory {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        System.out.println(threadMXBean.getCurrentThreadCpuTime());
        System.out.println(threadMXBean.getCurrentThreadUserTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(threadMXBean.getAllThreadIds());
    }

}
