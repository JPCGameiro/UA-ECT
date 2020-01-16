function [chave] = GeraChaves (nMin, nMax)
    alpha = ['a':'z' 'A':'Z'];
    size = randi([nMin, nMax]);
    chave = alpha(randi(numel(alpha), size, 1));
end

