<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="user.uname"/>
    <xsl:param name="user.surname" />
    <xsl:param name="os.name"/>
    <xsl:param name="os.arch"/>
    <xsl:param name="os.version"/>
    <xsl:param name="tstamp"/>

    <xsl:template match="testsuite">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <info>
                <user>
                    <xsl:value-of select="concat($user.uname, ' ', $user.surname)"/>
                </user>
                <os><xsl:value-of select="$os.name"/></os>
                <arch><xsl:value-of select="$os.arch"/></arch>
                <osversion><xsl:value-of select="$os.version"/></osversion>
                <timestamp><xsl:value-of select="$tstamp"/></timestamp>
            </info>
            <xsl:copy-of select="testcase"/>
            <xsl:copy-of select="system-out"/>
            <xsl:copy-of select="system-err"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>

