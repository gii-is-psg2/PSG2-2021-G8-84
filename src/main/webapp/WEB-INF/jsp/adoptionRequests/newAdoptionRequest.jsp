<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<petclinic:layout pageName="adoptions">
	<jsp:body>
	<h3>Petición de adopción de la mascota <c:out value="${adoption.pet.name} (${adoption.pet.type})"/></h3>
        <form:form modelAttribute="adoptionRequest" class="form-horizontal">
        
			<input type="hidden" name="ownerId" value="${owner.id}" />
			<input type="hidden" name="adoptionId" value="${adoption.id}" />
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Descripción" name="description"/>
				 
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">  
                    <button class="btn btn-default" type="submit">Guardar petición</button>
                </div>
            </div>
            
        </form:form>
    </jsp:body>
</petclinic:layout>