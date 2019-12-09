package poc.rcm.calculadoraservice.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class UTLocalCallbackHandler implements CallbackHandler {

	private Map<String, String> passwords = new HashMap<String, String>();

	public UTLocalCallbackHandler() {
		passwords.put("Alice", "ecilA");
		passwords.put("abcd", "dcba");
	}

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (Callback callback: callbacks) {
            WSPasswordCallback pc = (WSPasswordCallback)callback;
            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
	}

}
