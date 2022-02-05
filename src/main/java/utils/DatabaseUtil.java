package utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.ObjectUtils;

import com.alkemy.challenge.model.PeliculaoserieModel;
import com.alkemy.challenge.model.PersonajeModel;

public final class DatabaseUtil {
	private DatabaseUtil() {
	}
	
	public static Predicate addPredicateLike(String search,String atributeClass, CriteriaBuilder cb , Root<PersonajeModel> root , Predicate predicateMain) {
		if (!ObjectUtils.isEmpty(search)) {
			Predicate predicate = cb.like(root.get(atributeClass),"%"+search+"%");
			predicateMain = cb.and(predicateMain,predicate);
		}
		return predicateMain;
	}
	
	public static Predicate addPredicateMovieLike(String search,String atributeClass, CriteriaBuilder cb , Root<PeliculaoserieModel> root , Predicate predicateMain) {
		if (!ObjectUtils.isEmpty(search)) {
			Predicate predicate = cb.like(root.get(atributeClass),"%"+search+"%");
			predicateMain = cb.and(predicateMain,predicate);
		}
		return predicateMain;
	}
}
