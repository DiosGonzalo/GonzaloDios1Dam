 document.querySelector('form').addEventListener('submit', function(e) {
            const fechaExpedicion = document.getElementById('fechaExpedicion').value;
            const fechaLimite = document.getElementById('fechaLimite').value;
            
            if (fechaLimite > fechaExpedicion) {
                alert('La fecha límite no puede ser posterior a la fecha de expedición');
                e.preventDefault();
            }
        });