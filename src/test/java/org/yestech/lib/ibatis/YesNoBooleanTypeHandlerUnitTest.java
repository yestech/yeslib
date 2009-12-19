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

import java.sql.Types;

/*
 *
 * Author:  Greg Crow
 * Last Modified Date: $DateTime: $
 */
@RunWith(MockitoJUnitRunner.class)
public class YesNoBooleanTypeHandlerUnitTest
{
    private YesNoBooleanTypeHandler handler;

    @Before
    public void setup() {
        handler = new YesNoBooleanTypeHandler();
    }

    @Test
    public void testGetResultWith_Y() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getString()).thenReturn("Y");

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof Boolean);
        assertTrue((Boolean) result);
    }

    @Test
    public void testGetResultWith_N() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getString()).thenReturn("N");

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof Boolean);
        assertFalse((Boolean) result);
    }

    @Test
    public void testGetResultWith_y() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getString()).thenReturn("y");

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof Boolean);
        assertTrue((Boolean) result);
    }

    @Test
    public void testGetResultWith_n() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getString()).thenReturn("n");

        Object result = handler.getResult(getterMock);
        assertTrue(result instanceof Boolean);
        assertFalse((Boolean) result);
    }

    @Test
    public void testGetResultWithNoBoolVal() throws Exception
    {
        ResultGetter getterMock = mock(ResultGetter.class);
        when(getterMock.getString()).thenReturn("somedata");
        Object result = handler.getResult(getterMock);
        assertFalse(result instanceof Boolean);
        assertEquals("somedata", result.toString());
    }

    @Test
    public void testSetParameterWithNull() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, null);
        verify(setterMock, times(1)).setNull(Types.VARCHAR);
    }

    @Test
    public void testSetParameterWithTrue() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, true);
        verify(setterMock, times(1)).setString("Y");
    }

    @Test
    public void testSetParameterWithFalse() throws Exception
    {
        ParameterSetter setterMock = mock(ParameterSetter.class);
        handler.setParameter(setterMock, false);
        verify(setterMock, times(1)).setString("N");
    }
}
