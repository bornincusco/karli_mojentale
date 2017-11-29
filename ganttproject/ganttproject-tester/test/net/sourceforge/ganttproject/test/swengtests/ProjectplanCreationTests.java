package net.sourceforge.ganttproject.test.swengtests;

import biz.ganttproject.core.time.TimeDuration;
import biz.ganttproject.core.time.impl.GPTimeUnitStack;
import junit.framework.TestCase;
import net.sourceforge.ganttproject.TestSetupHelper;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProjectplanCreationTests extends TestCase {

    private TaskManager myTaskManager;

    private TaskManager getTaskManager() {
        return myTaskManager;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myTaskManager = newTaskManager();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        myTaskManager = null;
    }

    private TaskManager newTaskManager() {
        return TestSetupHelper.newTaskManagerBuilder().build();
    }


    /**
     * Here comes your implementation
     */
    public void createPlan() {


        Task result0 = getTaskManager().createTask();
        result0.move(getTaskManager().getRootTask());
        result0.setName(String.valueOf(result0.getTaskID()));
        result0.setDuration(getTaskManager().createLength(1));

        Task result1 = getTaskManager().createTask();
        result1.move(getTaskManager().getRootTask());
        result1.setName(String.valueOf(result1.getTaskID()));
        result1.setDuration(getTaskManager().createLength(1));

        Task result2 = getTaskManager().createTask();
        result2.move(getTaskManager().getRootTask());
        result2.setName(String.valueOf(result2.getTaskID()));
        result2.setDuration(getTaskManager().createLength(1));

        Task result3 = getTaskManager().createTask();
        result3.move(getTaskManager().getRootTask());
        result3.setName(String.valueOf(result3.getTaskID()));
        result3.setDuration(getTaskManager().createLength(1));

        Task result4= getTaskManager().createTask();
        result4.move(getTaskManager().getRootTask());
        result4.setName(String.valueOf(result4.getTaskID()));
        result4.setDuration(getTaskManager().createLength(4));

        getTaskManager().getDependencyCollection().createDependency(result3, result4);
        getTaskManager().getDependencyCollection().createDependency(result2, result3);

        result0.move(result1);

    }


    public void testThePlanIsInitiallyEmpty() {
        assertEquals(0, getTaskManager().getTasks().length);
    }

    public void testThePlanContains5Tasks() {
        createPlan();
        assertEquals(5, this.myTaskManager.getTasks().length);
    }

    public void testAccumulatedDurationIs8days() {
        createPlan();
        Task[] tasks = myTaskManager.getTasks();
        int durationInDays = 0;
        for (Task task : tasks) {
            System.out.println(task.getName() + task.getDuration());
            assertEquals(task.getDuration().getTimeUnit(), GPTimeUnitStack.DAY);
            durationInDays += task.getDuration().getLength();
        }

        assertEquals(8, durationInDays);

    }

    public void testTheOverallProjectLengthIs6Days() {
        createPlan();
        TimeDuration expectedProjectDuration = GPTimeUnitStack.createLength(GPTimeUnitStack.DAY, 6);
        assertEquals(expectedProjectDuration, myTaskManager.getProjectLength());
    }

    public void testItHasNestedTasks() {
        createPlan();
        boolean someTaskHasSubtasks = false;
        for (Task task : myTaskManager.getTasks()) {
            someTaskHasSubtasks = someTaskHasSubtasks || myTaskManager.getTaskHierarchy().hasNestedTasks(task);
        }

        assertTrue(someTaskHasSubtasks);
    }

    public void testHasSomeDependencies() {
        createPlan();
        boolean someTaskHaveDependencies = false;
        for (Task task: myTaskManager.getTasks()) {
            someTaskHaveDependencies = someTaskHaveDependencies || (task.getDependenciesAsDependant().toArray().length > 0);
        }
        assertTrue(someTaskHaveDependencies);
    }

}