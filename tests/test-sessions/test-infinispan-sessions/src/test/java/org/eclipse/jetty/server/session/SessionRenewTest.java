//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//


package org.eclipse.jetty.server.session;

import static org.junit.Assert.assertTrue;

import org.eclipse.jetty.webapp.WebAppContext;

import static org.junit.Assert.assertFalse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SessionRenewTest
 *
 *
 */
public class SessionRenewTest extends AbstractSessionRenewTest
{

    public static InfinispanTestSupport __testSupport;
    
    
    @BeforeClass
    public static void setup () throws Exception
    {
        __testSupport = new InfinispanTestSupport();
        __testSupport.setup();
    }
    
    @AfterClass
    public static void teardown () throws Exception
    {
        __testSupport.teardown();
    }
    
    
    /** 
     * @see org.eclipse.jetty.server.session.AbstractSessionRenewTest#createServer(int, int, int)
     */
    @Override
    public AbstractTestServer createServer(int port, int max, int scavenge, int idlePassivateInterval)
    {
        return new InfinispanTestSessionServer(port, max, scavenge, idlePassivateInterval, __testSupport.getCache());
    }

    @Test
    public void testSessionRenewal() throws Exception
    {
        super.testSessionRenewal();
    }

    /** 
     * @see org.eclipse.jetty.server.session.AbstractSessionRenewTest#verifyChange(java.lang.String, java.lang.String)
     */
    @Override
    public boolean verifyChange(WebAppContext context, String oldSessionId, String newSessionId)
    {
        assertTrue(((InfinispanTestSessionServer)_server).exists(context, newSessionId));
        assertFalse(((InfinispanTestSessionServer)_server).exists(context, oldSessionId));
       return (!((InfinispanTestSessionServer)_server).exists(context, oldSessionId)) && ((InfinispanTestSessionServer)_server).exists(context, newSessionId);
    }

    
}
