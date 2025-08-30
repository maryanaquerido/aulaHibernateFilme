package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Filme;

public class FilmeDao implements IFilmeDao{

	EntityManagerFactory mf = Persistence.createEntityManagerFactory ("HibJPA");


	public Filme pesquisar(String filme) {
		return null;
	}

	public List<Filme> lista() {
		EntityManager em = mf.createEntityManager();
		List<Filme> filmes = em.createQuery("SELECT f FROM Filme f", Filme.class).getResultList();
		em.close();
		return filmes;
	}
	
	public List<Filme> apenasUmFilme (String titulo){
		EntityManager em = mf.createEntityManager();
		//precisamos da lista porque estamos fazendo um select sem o where
		List<Filme> filmes = em.createQuery("SELECT f FROM Filme f WHERE f.titulo LIKE :titulo", Filme.class)
				.setParameter("titulo", "%" + titulo + "%")
				.getResultList();
		em.close();
		return filmes;
	}

	public void inserir(Filme filme) {
		EntityManager em = mf.createEntityManager();
		em.getTransaction().begin();
		em.persist(filme);
		em.getTransaction().commit();
		em.close();
	}

	//remover filme. como estou mandando apenas o id preciso achar quem e o objeto
	public void remover(long id) {
		EntityManager em = mf.createEntityManager();
		em.getTransaction().begin();
		//procurar o filme pelo id
		Filme filme = em.find(Filme.class, id);//tipo um select com where do sql
		if (filme != null) {
			em.remove(filme);
		}
		em.getTransaction().commit(); // confirmar a transação, comitar
		em.close(); // fechar o EntityManager
	}

	public void atualizar(Filme filme) {
		EntityManager em = mf.createEntityManager();
		em.getTransaction().begin();
		em.merge(filme); // tipo um update do sql
		em.getTransaction().commit();
		em.close();
	}
	
}
