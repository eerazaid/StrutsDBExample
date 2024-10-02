<!-- footer.jsp -->
    <footer>
        
        <p>&copy; 2024. All rights reserved.</p>
        <p><a>Privacy Policy</a> | <a>Terms of Service</a></p>
    </footer>
    	<script src="<%= request.getContextPath() %>/js/script.js"></script>
		<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
		<script src="https://cdn.datatables.net/2.1.7/js/dataTables.js"></script>
		<script src="https://cdn.datatables.net/buttons/3.1.2/js/dataTables.buttons.js"></script>
		<script src="https://cdn.datatables.net/buttons/3.1.2/js/buttons.dataTables.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>
		<script src="https://cdn.datatables.net/buttons/3.1.2/js/buttons.html5.min.js"></script>
		
		<script>
		$(document).ready(function() {
		    new DataTable('#userTable', {
		        layout: {
		            topStart: {
		                buttons: ['copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5']
		            }
		        }
		    });
		});
		</script>

</body>
</html>
