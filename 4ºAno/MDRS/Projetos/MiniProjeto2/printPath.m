% recebe um path, array de inteiros, e imprime na mesma linha com sep a
% separar
function printPath(path, sep)
    aux = '';
    for i = 1:length(path)
        fprintf('%s', aux);
           fprintf('%d', path(i))
        aux = sep;
    end
end