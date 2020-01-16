function [h] = hashFunction(str,n)
    num2str = string([1:n]);
    for i=1:n
        str = [str num2str(i)];
        h = string2hash()
    end
end

