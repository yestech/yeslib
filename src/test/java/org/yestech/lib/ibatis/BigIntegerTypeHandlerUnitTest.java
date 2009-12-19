/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.ibatis;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;

/*
 *
 * Author:  Greg Crow
 * Last Modified Date: $DateTime: $
 */
@RunWith(MockitoJUnitRunner.class)
public class BigIntegerTypeHandlerUnitTest
{
    private BigIntegerTypeHandler handler;

    @Before
    public void setup() {
        handler = new BigIntegerTypeHandler();
    }

    @Test
    public void testGetResultWithBigDecimal() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getObject()).thenReturn(BigDecimal.ONE);

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof BigInteger);
        assertEquals(BigInteger.ONE, result);
    }

    @Test
    public void testGetResultWithString() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getObject()).thenReturn("10");

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof BigInteger);
        assertEquals(BigInteger.TEN, result);
    }

    @Test
    public void testGetResultWithNoHandling() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getObject()).thenReturn(new Integer(2));

        Object result = handler.getResult(getterMock);
        assertFalse(result instanceof BigInteger);
    }

    @Test
    public void testSetParameterWithNull() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, null);
        verify(setterMock, times(1)).setNull(Types.BIGINT);
    }

    @Test
    public void testSetParameterWithBigInteger() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, BigInteger.ONE);
        verify(setterMock, times(1)).setBigDecimal(BigDecimal.ONE);
    }

    @Test
    public void testSetParameterWithUnknownType() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, new Integer(2));
        verify(setterMock, times(1)).setNull(Types.BIGINT);
    }
}
