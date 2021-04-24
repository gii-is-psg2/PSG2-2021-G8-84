<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<petclinic:layout pageName="adoptions">
	<jsp:body>
        <form:form modelAttribute="adoption" class="form-horizontal">
        
			<input type="hidden" name="ownerId" value="${owner.id}" />
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Descripción" name="description"/>
					
                <spring:bind path="pet">
                <label for="pet">Elige tu mascota:</label>
                        <select name="pet">
   							<c:forEach items="${pets}" var="pet">  
   						 		<option value="${pet.id}">${pet.name}</option>)
							</c:forEach>
    					</select>
				</spring:bind>	<br>
				 
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">  
                    <button class="btn btn-default" type="submit">Guardar adopción</button>
                </div>
            </div>
            
        </form:form>
    </jsp:body>
</petclinic:layout>