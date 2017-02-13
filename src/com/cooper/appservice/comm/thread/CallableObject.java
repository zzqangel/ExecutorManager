package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.watch.ServiceStopThread;
import org.apache.http.annotation.GuardedBy;
import com.cooper.comm.MainService;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-26
 * Time: ÏÂÎç5:01
 * To change this template use File | Settings | File Templates.
 */
public class CallableObject<E> {
    @GuardedBy("this")
    private RunnableObject<E> runnableObject;

    public void setRunnableObject(RunnableObject<E> runnableObject) {
        this.runnableObject = runnableObject;
    }

    public boolean cancelTask(boolean mayInterruptIfRunning) {
        if(runnableObject != null) {
            if(runnableObject.removeFromTaskCollection()) {
                return true;
            }
            if(mayInterruptIfRunning) {
                new MainService().addRunTask(new ServiceStopThread(this.runnableObject), 30);
            }
        }
        return false;
    }

    private E e = null;

    private boolean getResultFlg = false;

    public void setReturnValue(E e) {
        this.e = e;
        this.getResultFlg = true;
    }

    public boolean isGetResultFlg() {
        return getResultFlg;
    }

    public E getResult() {
        return e;
    }

}