import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.agent.api.EntryTypes;
import com.appdynamics.agent.api.Transaction;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;

import java.util.ArrayList;
import java.util.List;

public class Interceptor extends AGenericInterceptor {
    @Override
    public Object onMethodBegin(Object objectIntercepted, String className, String methodName, Object[] params) {

        if( methodName.equals("runBusinessTransaction") ) {
            Transaction transaction = AppdynamicsAgent.startTransaction("placeholder-toBeRenamed", null, EntryTypes.POJO, false);
            getLogger().info("Started BT placeholder guid: "+ transaction.getUniqueIdentifier());
            return transaction;
        }

        if( methodName.equals("getRandomSleepSeconds") ) {
            return null;
        }
        return null;
    }

    @Override
    public void onMethodEnd(Object state, Object objectIntercepted, String className, String methodName, Object[] params, Throwable exception, Object returnVal) {
        if( methodName.equals("runBusinessTransaction") ) {
            if( state == null ) return;
            //Transaction transaction = (Transaction) state; //this is the return of the begin method, it may no longer be valid so....
            Transaction transaction = AppdynamicsAgent.getTransaction(); //now i'm getting the active BT from the agent
            getLogger().info("Ending BT guid: "+ transaction.getUniqueIdentifier());
            transaction.end();
        }

        if( methodName.equals("getRandomSleepSeconds") ) {
            String firstGuid = AppdynamicsAgent.getTransaction().getUniqueIdentifier();
            AppdynamicsAgent.setCurrentTransactionName(String.format("finalBTName-%s", String.valueOf(returnVal)));
            getLogger().info(String.format("Renamed BT first Guid: %s final Guid: %s", firstGuid, AppdynamicsAgent.getTransaction().getUniqueIdentifier()));
        }
    }

    @Override
    public List<Rule> initializeRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule.Builder(
                "TestApplication")
                .classMatchType(SDKClassMatchType.MATCHES_CLASS)
                .methodMatchString("runBusinessTransaction")
                .build()
        );
        rules.add(new Rule.Builder(
                "TestApplication")
                .classMatchType(SDKClassMatchType.MATCHES_CLASS)
                .methodMatchString("getRandomSleepSeconds")
                .build()
        );
        return rules;
    }
}
