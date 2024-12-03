package daodb4o;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

public abstract class DAO<T> implements DAOInterface<T> {
	protected static ObjectContainer manager;

	public static void open() {
		manager = Util.conectarBanco(); // banco local
		//manager = Util.conectarBancoRemoto(); //banco remoto
	}

	public static void close() {
		Util.desconectar();
	}

	// ----------CRUD-----------------------

	public void create(T obj) {
		manager.store(obj);
	}

	public T update(T obj) {
		manager.store(obj);
		return obj;
	}

	public void delete(T obj) {
		manager.delete(obj);
	}

	public void refresh(T obj) {
		manager.ext().refresh(obj, Integer.MAX_VALUE);
	}

	@SuppressWarnings("unchecked")
	public List<T> readAll() {
		manager.ext().purge(); // limpar cache do manager

		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Query q = manager.query();
		q.constrain(type);
		return (List<T>) q.execute();
	}

	// --------transa��o---------------
	public static void begin() {
	} // tem que ser vazio

	public static void commit() {
		manager.commit();
		manager.ext().purge();
	}

	public static void rollback() {
		manager.rollback();
	}

	public <X> int gerarId(Class<X> classe) {
	    // Verificar se o banco está vazio
	    if (manager.query(classe).isEmpty()) {
	        return 1; // primeiro id da classe
	    } else {
	        // Obter o maior id da classe
	        Query q = manager.query();
	        q.constrain(classe);

	        // Tentar ordenar pelo campo "id"
	        try {
	            q.descend("id").orderDescending();
	            List<X> resultados = q.execute();
	            if (!resultados.isEmpty()) {
	                X objeto = resultados.get(0); // Pegar o primeiro objeto com maior id
	                Field idField = getIdField(classe);
	                idField.setAccessible(true);
	                return idField.getInt(objeto) + 1;
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao acessar o campo 'id' para classe: " + classe.getName(), e);
	        }

	        // Caso algo falhe, retornar um novo ID básico
	        throw new RuntimeException("Não foi possível calcular o próximo ID para a classe: " + classe.getName());
	    }
	}

	private <X> Field getIdField(Class<X> classe) throws NoSuchFieldException {
	    for (Field f : getAllFieldsList(classe)) {
	        if (f.getName().equals("id")) {
	            return f;
	        }
	    }
	    throw new NoSuchFieldException("Campo 'id' não encontrado na classe: " + classe.getName());
	}

	
	/**
	 * gerar novo id para o tipo T, baseando-se no maior valor do id
	 * 
	 */
	
/*	
	public <X> int gerarId(Class<X> classe) {
		
		// verificar se o banco esta vazio
		if (manager.query(classe).isEmpty()) {
			return 1; 	// primeiro id da classe
		} 
		else {
			// obter o maior id da classe
			Query q = manager.query();
			q.constrain(classe);
			q.descend("id").orderDescending();
			List<X> resultados = q.execute();
			if (resultados.isEmpty())
				return 1; // retorna primeiro id
			else
				try {
					// obter objeto com maior id
					X objeto = resultados.getFirst();
					//localizar atributo id dentro do objeto
					int id = 0;
					for (Field f : getAllFieldsList(classe)) {
						if (f.getName().equals("id")) {
							f.setAccessible(true);		//atributo private
							id = (Integer)f.get(objeto);
						}
					}
					if (id == 0)
						throw new NoSuchFieldException();
					
					return id+1;
				} catch (NoSuchFieldException e) {
					throw new RuntimeException("classe " + classe + " - nao tem atributo id");
				} catch (IllegalAccessException e) {
					throw new RuntimeException("classe " + classe + " - atributo id inacessivel");
				}
		}
	}*/

	public static <X> List<Field> getAllFieldsList(final Class<X> cls) {
		// retorna uma lista com todos os campos do objeto
		final List<Field> allFields = new ArrayList<>();
		Class<?> currentClass = cls;
		while (currentClass != null) {
			final Field[] declaredFields = currentClass.getDeclaredFields();
			Collections.addAll(allFields, declaredFields);
			currentClass = currentClass.getSuperclass();
		}
		return allFields;
	}
}
