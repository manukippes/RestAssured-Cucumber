@BusquedaProductos
Feature: SERVICIO - Busqueda de productos Mercado Libre Argentina

@High 
  Scenario Outline: Verificar resultados del servicio de busqueda de productos de mercado libre Argentina
    Given El servicio de busqueda de productos de mercado libre Argentina con el criterio de busqueda "<criterioBusqueda>"
    Then Nos arroja como resultado productos que coinciden con el criterio de busqueda
    
    Examples:
     	| criterioBusqueda     | 
    	| Redmi Note 7         |
    	| volante logitech g29 |
    	| teclado				       |
    	| redragon             |