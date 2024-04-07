<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>JUnit Test Report</title>
                <style type="text/css">
                    body, table {font-family: arial, sans-serif; font-size: 12px;}
                    table {border-collapse: collapse; width: 100%;}
                    th, td {border: 1px solid silver; padding: 4px; text-align: left;}
                    th {background-color: #F0F0F0;}
                    tr:nth-child(even) {background-color: #F9F9F9;}
                    .fail {color: red;}
                    .pass {color: green;}
                </style>
            </head>
            <body>
                <h2>JUnit Test Report</h2>
                <table>
                    <tr>
                        <th>Test Case</th>
                        <th>Status</th>
                        <th>Time (seconds)</th>
                    </tr>
                    <xsl:apply-templates select="//testcase"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="testcase">
        <tr>
            <td><xsl:value-of select="@name"/></td>
            <td>
                <xsl:choose>
                    <xsl:when test="failure">
                        <span class="fail">Failed</span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span class="pass">Passed</span>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td><xsl:value-of select="@time"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
