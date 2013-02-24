package org.cuckoo.ra.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.resource.cci.MappedRecord;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuckooBean {
    /**
     * The JNDI name might have to be changed to work with a specific application server.
     */
//    private static final String CONNECTION_FACTORY_JNDI_NAME = "java:jboss/eis/sap/NSP"; // works with JBoss
    private static final String CONNECTION_FACTORY_JNDI_NAME = "eis/sap/NSP"; // works with Glassfish

    private static final Logger LOGGER = Logger.getLogger( CuckooBean.class.getName() );

    private final SapData data = new SapData();

    public String getRfcHost() throws ResourceException, NamingException {
        return data.getRfcHost();
    }

    public String getIpAddress() throws ResourceException, NamingException {
        return data.getIpAddress();
    }

    public String getSapRelease() throws ResourceException, NamingException {
        return data.getSapRelease();
    }

    public void loadData() throws NamingException, ResourceException {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup( CONNECTION_FACTORY_JNDI_NAME );

        LOGGER.info( "Cuckoo - ConnectionFactory=" + connectionFactory );

        MappedRecord input = connectionFactory.getRecordFactory().createMappedRecord( "RFC_SYSTEM_INFO" );

        Connection connection = connectionFactory.getConnection();

        Interaction interaction = connection.createInteraction();

        try {
            MappedRecord output = (MappedRecord) interaction.execute( null, input );
            LOGGER.info( "result=" + output );

            MappedRecord rfcsi = (MappedRecord) output.get( "RFCSI_EXPORT" );
            LOGGER.info( "rfcsi=" + rfcsi );
            LOGGER.info( "rfcHost=" + rfcsi.get( "RFCHOST" ) );

            data.setRfcHost( (String) rfcsi.get( "RFCHOST" ) );
            data.setIpAddress( (String) rfcsi.get( "RFCIPADDR" ) );
            data.setSapRelease( (String) rfcsi.get( "RFCSAPRL" ) );
        } finally {
            closeInteraction( interaction );
            closeConnection( connection );
        }
    }

    private void closeConnection( Connection connection ) {
        if ( connection != null ) {
            try {
                connection.close();
            } catch ( ResourceException closeException ) {
                LOGGER.log( Level.WARNING,
                            "Error closing the connection. " +
                                    "Open connections might remain in the connection pool" +
                                    " which results in the connection pool being exhausted.",
                            closeException );
            }
        }
    }

    private void closeInteraction( Interaction interaction ) {
        if ( interaction != null ) {
            try {
                interaction.close();
            } catch ( ResourceException closeException ) {
                LOGGER.log( Level.WARNING, "Error closing the interaction.", closeException );
            }
        }
    }
}
