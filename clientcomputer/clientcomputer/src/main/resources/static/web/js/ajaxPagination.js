$(document).ready(function(){
	let totalPages = 1;
	console.log();
	
	function fetchProducts(startPage){
		if($(location).attr('href').includes("category"))
		$.ajax({
	        type : "GET",
	        url : "http://localhost:8081/api/product/page",
	        data: { 
	            page: startPage+1
	        },
	        success: function(response){
	          $('#productTable tbody').empty();
	          // add table rows
	          $.each(response.content, (i, product) => {
	            let noteRow = '<tr id="row'+product.id+'">' +
	      	  						'<td class="rower">' + 
	      	  							'<input type="hidden" id="productId" name="productId" value="'+product.id+'">' +
										'<span>' + product.name + '</span>' + 
	      	  						'</td>' +
			                		'<td>' + 
			                			'<button type="button" class="btn btn-success btnGetProduct">'+
											'<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">' +
											'<path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"></path>' +
											'</svg>' +
										'</button>' + 
			                		'</td>' +
			                   '</tr>';
	            $('#productTable tbody').append(noteRow);
	          });

	          if ($('ul.pagination li').length - 2 != response.totalPages){
	          	  // build pagination list at the first time loading
	        	  $('ul.pagination').empty();
	              buildPagination(response);
	          }
	        },
	        error : function(e) {
	          alert("ERROR: ", e);
	          console.log("ERROR: ", e);
	        }
	    });
	}
})