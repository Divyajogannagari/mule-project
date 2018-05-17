package com.murphy.mulesoft.LDAPTransformer;

import org.mule.module.ldap.api.LDAPEntry;
import org.mule.module.ldap.api.LDAPEntryAttribute;
import org.mule.streaming.Consumer;
import org.mule.streaming.ConsumerIterator;
import org.mule.module.ldap.api.LDAPEntryAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;


public class LDAPEntryToHashMap implements Callable {
	Map<Object, Object> resultSet = null;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		LDAPEntry entry = new LDAPEntry();
		List<Object> list = new ArrayList<Object>();
		List<Object> ldaplist = new ArrayList<Object>();

	list = (List<Object>) eventContext.getMessage().getPayload();

		for (int i = 0; i < list.size(); i++) {
			resultSet = new HashMap<Object, Object>();
			entry = (LDAPEntry) list.get(i);
			LDAPEntryAttributes ldapattr = entry.getAttributes();
			ldapattr.attributes().forEachRemaining(s -> {
				resultSet.put(s.getName().toString(), s.getValue().toString());
			});
			ldaplist.add(resultSet);
		}
		return ldaplist;
	}
}