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
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.sql.Types;

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
public abstract class EnumTypeHandler<E extends Enum> implements TypeHandlerCallback
{
	private Class<E> enumClass_;
	
	public EnumTypeHandler(Class<E> enumClass)
	{
		enumClass_ = enumClass;
	}

	@SuppressWarnings("unchecked")
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException
	{
		setter.setString(((E) parameter).name());
	}

	public Object getResult(ResultGetter getter) throws SQLException
	{
		return valueOf(getter.getString());
	}

	@SuppressWarnings("unchecked")
	public Object valueOf(String s)
	{
		return Enum.valueOf(enumClass_, s);
	}
}