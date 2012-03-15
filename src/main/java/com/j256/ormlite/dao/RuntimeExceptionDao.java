package com.j256.ormlite.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Proxy to a {@link Dao} that wraps each Exception and rethrows it as RuntimeException. You can use this if your usage
 * pattern is to ignore all exceptions. That's not a pattern that I like so it's not the default.
 * 
 * <p>
 * 
 * <pre>
 * RuntimeExceptionDao&lt;Account, String&gt; accountDao = RuntimeExceptionDao.createDao(connectionSource, Account.class);
 * </pre>
 * 
 * </p>
 * 
 * @author graywatson
 */
public class RuntimeExceptionDao<T, ID> {

	/*
	 * We use debug here because we don't want these messages to be logged by default. The user will need to turn on
	 * logging for this class to DEBUG to see the messages.
	 */
	private static final Level LOG_LEVEL = Level.DEBUG;

	private Dao<T, ID> dao;
	private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptionDao.class);

	public RuntimeExceptionDao(Dao<T, ID> dao) {
		this.dao = dao;
	}

	/**
	 * Call through to {@link DaoManager#createDao(ConnectionSource, Class)} with the returned DAO wrapped in a
	 * RuntimeExceptionDao.
	 */
	public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource, Class<T> clazz)
			throws SQLException {
		@SuppressWarnings("unchecked")
		Dao<T, ID> castDao = (Dao<T, ID>) DaoManager.createDao(connectionSource, clazz);
		return new RuntimeExceptionDao<T, ID>(castDao);
	}

	/**
	 * Call through to {@link DaoManager#createDao(ConnectionSource, DatabaseTableConfig)} with the returned DAO wrapped
	 * in a RuntimeExceptionDao.
	 */
	public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource,
			DatabaseTableConfig<T> tableConfig) throws SQLException {
		@SuppressWarnings("unchecked")
		Dao<T, ID> castDao = (Dao<T, ID>) DaoManager.createDao(connectionSource, tableConfig);
		return new RuntimeExceptionDao<T, ID>(castDao);
	}

	/**
	 * @see Dao#queryForId(Object)
	 */
	public T queryForId(ID id) {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			logMessage(e, "queryForId threw exception on: " + id);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForFirst(PreparedQuery)
	 */
	public T queryForFirst(PreparedQuery<T> preparedQuery) {
		try {
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			logMessage(e, "queryForFirst threw exception on: " + preparedQuery);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForAll()
	 */
	public List<T> queryForAll() {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			logMessage(e, "queryForAll threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForEq(String, Object)
	 */
	public List<T> queryForEq(String fieldName, Object value) {
		try {
			return dao.queryForEq(fieldName, value);
		} catch (SQLException e) {
			logMessage(e, "queryForEq threw exception on: " + fieldName);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForMatching(Object)
	 */
	public List<T> queryForMatching(T matchObj) {
		try {
			return dao.queryForMatching(matchObj);
		} catch (SQLException e) {
			logMessage(e, "queryForMatching threw exception on: " + matchObj);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForMatchingArgs(Object)
	 */
	public List<T> queryForMatchingArgs(T matchObj) {
		try {
			return dao.queryForMatchingArgs(matchObj);
		} catch (SQLException e) {
			logMessage(e, "queryForMatchingArgs threw exception on: " + matchObj);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForFieldValues(Map)
	 */
	public List<T> queryForFieldValues(Map<String, Object> fieldValues) {
		try {
			return dao.queryForFieldValues(fieldValues);
		} catch (SQLException e) {
			logMessage(e, "queryForFieldValues threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForFieldValuesArgs(Map)
	 */
	public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) {
		try {
			return dao.queryForFieldValuesArgs(fieldValues);
		} catch (SQLException e) {
			logMessage(e, "queryForFieldValuesArgs threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryForSameId(Object)
	 */
	public T queryForSameId(T data) {
		try {
			return dao.queryForSameId(data);
		} catch (SQLException e) {
			logMessage(e, "queryForSameId threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryBuilder()
	 */
	public QueryBuilder<T, ID> queryBuilder() {
		return dao.queryBuilder();
	}

	/**
	 * @see Dao#updateBuilder()
	 */
	public UpdateBuilder<T, ID> updateBuilder() {
		return dao.updateBuilder();
	}

	/**
	 * @see Dao#deleteBuilder()
	 */
	public DeleteBuilder<T, ID> deleteBuilder() {
		return dao.deleteBuilder();
	}

	/**
	 * @see Dao#query(PreparedQuery)
	 */
	public List<T> query(PreparedQuery<T> preparedQuery) {
		try {
			return dao.query(preparedQuery);
		} catch (SQLException e) {
			logMessage(e, "query threw exception on: " + preparedQuery);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#create(Object)
	 */
	public int create(T data) {
		try {
			return dao.create(data);
		} catch (SQLException e) {
			logMessage(e, "create threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#createIfNotExists(Object)
	 */
	public T createIfNotExists(T data) {
		try {
			return dao.createIfNotExists(data);
		} catch (SQLException e) {
			logMessage(e, "createIfNotExists threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#createOrUpdate(Object)
	 */
	public CreateOrUpdateStatus createOrUpdate(T data) {
		try {
			return dao.createOrUpdate(data);
		} catch (SQLException e) {
			logMessage(e, "createOrUpdate threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#update(Object)
	 */
	public int update(T data) {
		try {
			return dao.update(data);
		} catch (SQLException e) {
			logMessage(e, "update threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#updateId(Object, Object)
	 */
	public int updateId(T data, ID newId) {
		try {
			return dao.updateId(data, newId);
		} catch (SQLException e) {
			logMessage(e, "updateId threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#update(PreparedUpdate)
	 */
	public int update(PreparedUpdate<T> preparedUpdate) {
		try {
			return dao.update(preparedUpdate);
		} catch (SQLException e) {
			logMessage(e, "update threw exception on: " + preparedUpdate);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#refresh(Object)
	 */
	public int refresh(T data) {
		try {
			return dao.refresh(data);
		} catch (SQLException e) {
			logMessage(e, "refresh threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#delete(Object)
	 */
	public int delete(T data) {
		try {
			return dao.delete(data);
		} catch (SQLException e) {
			logMessage(e, "delete threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#deleteById(Object)
	 */
	public int deleteById(ID id) {
		try {
			return dao.deleteById(id);
		} catch (SQLException e) {
			logMessage(e, "deleteById threw exception on: " + id);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#delete(Collection)
	 */
	public int delete(Collection<T> datas) {
		try {
			return dao.delete(datas);
		} catch (SQLException e) {
			logMessage(e, "delete threw exception on: " + datas);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#deleteIds(Collection)
	 */
	public int deleteIds(Collection<ID> ids) {
		try {
			return dao.deleteIds(ids);
		} catch (SQLException e) {
			logMessage(e, "deleteIds threw exception on: " + ids);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#delete(PreparedDelete)
	 */
	public int delete(PreparedDelete<T> preparedDelete) {
		try {
			return dao.delete(preparedDelete);
		} catch (SQLException e) {
			logMessage(e, "delete threw exception on: " + preparedDelete);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#iterator()
	 */
	public CloseableIterator<T> iterator() {
		return dao.iterator();
	}

	/**
	 * @see Dao#iterator(int)
	 */
	public CloseableIterator<T> iterator(int resultFlags) {
		return dao.iterator(resultFlags);
	}

	/**
	 * @see Dao#getWrappedIterable()
	 */
	public CloseableWrappedIterable<T> getWrappedIterable() {
		return dao.getWrappedIterable();
	}

	/**
	 * @see Dao#getWrappedIterable(PreparedQuery)
	 */
	public CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> preparedQuery) {
		return dao.getWrappedIterable(preparedQuery);
	}

	/**
	 * @see Dao#closeLastIterator()
	 */
	public void closeLastIterator() {
		try {
			dao.closeLastIterator();
		} catch (SQLException e) {
			logMessage(e, "closeLastIterator threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#iterator(PreparedQuery)
	 */
	public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) {
		try {
			return dao.iterator(preparedQuery);
		} catch (SQLException e) {
			logMessage(e, "iterator threw exception on: " + preparedQuery);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#iterator(PreparedQuery, int)
	 */
	public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int resultFlags) {
		try {
			return dao.iterator(preparedQuery, resultFlags);
		} catch (SQLException e) {
			logMessage(e, "iterator threw exception on: " + preparedQuery);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryRaw(String, String...)
	 */
	public GenericRawResults<String[]> queryRaw(String query, String... arguments) {
		try {
			return dao.queryRaw(query, arguments);
		} catch (SQLException e) {
			logMessage(e, "queryRaw threw exception on: " + query);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryRaw(String, RawRowMapper, String...)
	 */
	public <UO> GenericRawResults<UO> queryRaw(String query, RawRowMapper<UO> mapper, String... arguments) {
		try {
			return dao.queryRaw(query, mapper, arguments);
		} catch (SQLException e) {
			logMessage(e, "queryRaw threw exception on: " + query);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#queryRaw(String, DataType[], String...)
	 */
	public GenericRawResults<Object[]> queryRaw(String query, DataType[] columnTypes, String... arguments) {
		try {
			return dao.queryRaw(query, columnTypes, arguments);
		} catch (SQLException e) {
			logMessage(e, "queryRaw threw exception on: " + query);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#executeRaw(String, String...)
	 */
	public int executeRaw(String statement, String... arguments) {
		try {
			return dao.executeRaw(statement, arguments);
		} catch (SQLException e) {
			logMessage(e, "executeRaw threw exception on: " + statement);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#updateRaw(String, String...)
	 */
	public int updateRaw(String statement, String... arguments) {
		try {
			return dao.updateRaw(statement, arguments);
		} catch (SQLException e) {
			logMessage(e, "updateRaw threw exception on: " + statement);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#callBatchTasks(Callable)
	 */
	public <CT> CT callBatchTasks(Callable<CT> callable) {
		try {
			return dao.callBatchTasks(callable);
		} catch (Exception e) {
			logMessage(e, "callBatchTasks threw exception on: " + callable);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#objectToString(Object)
	 */
	public String objectToString(T data) {
		return dao.objectToString(data);
	}

	/**
	 * @see Dao#objectsEqual(Object, Object)
	 */
	public boolean objectsEqual(T data1, T data2) {
		try {
			return dao.objectsEqual(data1, data2);
		} catch (SQLException e) {
			logMessage(e, "objectsEqual threw exception on: " + data1 + " and " + data2);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#extractId(Object)
	 */
	public ID extractId(T data) {
		try {
			return dao.extractId(data);
		} catch (SQLException e) {
			logMessage(e, "extractId threw exception on: " + data);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#getDataClass()
	 */
	public Class<T> getDataClass() {
		return dao.getDataClass();
	}

	/**
	 * @see Dao#findForeignFieldType(Class)
	 */
	public FieldType findForeignFieldType(Class<?> clazz) {
		return dao.findForeignFieldType(clazz);
	}

	/**
	 * @see Dao#isUpdatable()
	 */
	public boolean isUpdatable() {
		return dao.isUpdatable();
	}

	/**
	 * @see Dao#isTableExists()
	 */
	public boolean isTableExists() {
		try {
			return dao.isTableExists();
		} catch (SQLException e) {
			logMessage(e, "isTableExists threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#countOf()
	 */
	public long countOf() {
		try {
			return dao.countOf();
		} catch (SQLException e) {
			logMessage(e, "countOf threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#countOf(PreparedQuery)
	 */
	public long countOf(PreparedQuery<T> preparedQuery) {
		try {
			return dao.countOf(preparedQuery);
		} catch (SQLException e) {
			logMessage(e, "countOf threw exception on " + preparedQuery);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#getEmptyForeignCollection(String)
	 */
	public <FT> ForeignCollection<FT> getEmptyForeignCollection(String fieldName) {
		try {
			return dao.getEmptyForeignCollection(fieldName);
		} catch (SQLException e) {
			logMessage(e, "getEmptyForeignCollection threw exception on " + fieldName);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#setObjectCache(boolean)
	 */
	public void setObjectCache(boolean enabled) {
		try {
			dao.setObjectCache(enabled);
		} catch (SQLException e) {
			logMessage(e, "setObjectCache(" + enabled + ") threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#getObjectCache()
	 */
	public ObjectCache getObjectCache() {
		return dao.getObjectCache();
	}

	/**
	 * @see Dao#setObjectCache(ObjectCache)
	 */
	public void setObjectCache(ObjectCache objectCache) {
		try {
			dao.setObjectCache(objectCache);
		} catch (SQLException e) {
			logMessage(e, "setObjectCache threw exception on " + objectCache);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#clearObjectCache()
	 */
	public void clearObjectCache() {
		dao.clearObjectCache();
	}

	/**
	 * @see Dao#mapSelectStarRow(DatabaseResults)
	 */
	public T mapSelectStarRow(DatabaseResults results) {
		try {
			return dao.mapSelectStarRow(results);
		} catch (SQLException e) {
			logMessage(e, "mapSelectStarRow threw exception on results");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#getSelectStarRowMapper()
	 */
	public GenericRowMapper<T> getSelectStarRowMapper() {
		try {
			return dao.getSelectStarRowMapper();
		} catch (SQLException e) {
			logMessage(e, "getSelectStarRowMapper threw exception");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see Dao#idExists(Object)
	 */
	public boolean idExists(ID id) {
		try {
			return dao.idExists(id);
		} catch (SQLException e) {
			logMessage(e, "idExists threw exception on " + id);
			throw new RuntimeException(e);
		}
	}

	private void logMessage(Exception e, String message) {
		logger.log(LOG_LEVEL, e, message);
	}
}
