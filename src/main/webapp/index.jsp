<jsp:useBean id="bean" class="org.cuckoo.ra.test.CuckooBean" scope="session"/>
<html>
<body>
<h2>Cuckoo Test</h2>

<% bean.loadData(); %>

<p>

<h3>SAP Data returned by RFC_SYSTEM_INFO:</h3>
<dl>
    <dt>RFC Host</dt>
    <dd>'<jsp:getProperty name="bean" property="rfcHost"/>'</dd>
</dl>
<dl>
    <dt>IP Address</dt>
    <dd>'<jsp:getProperty name="bean" property="ipAddress"/>'</dd>
</dl>
<dl>
    <dt>SAP Release</dt>
    <dd>'<jsp:getProperty name="bean" property="sapRelease"/>'</dd>
</dl>
</p>
</body>
</html>
