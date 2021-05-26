<%@page import="java.time.LocalDate"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<petclinic:layout pageName="Donations">
	<jsp:body>
        
        <h3>Donación máxima: ${max}</h3>
        
        <form:form modelAttribute="donation" class="form-horizontal">
			 <input type="hidden" name="ownerId" value="${owner.id}" />
			 <input type="hidden" name="date" value="${date}" />
            <div class="form-group has-feedback">
                <petclinic:inputField label="Cantidad"
					name="amount"/>
				<p>Por favor, introduzca los céntimos también, utilice un punto para ello.</p>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                         
                    <button class="btn btn-default" type="submit">Confirmar donación</button>
                </div>
            </div>
        </form:form>
        
        
    
    </jsp:body>

</petclinic:layout>